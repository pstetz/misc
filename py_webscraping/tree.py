# Removes warnings that occassionally show in imports
import warnings
warnings.filterwarnings('ignore')

# Libraries for website scraping
from bs4 import BeautifulSoup
import requests
import urllib

#################### Configure me! ####################
# NOTE: if you add a website, there needs to be a function to find members' names and locations
websites = [
            ("Kennet", "https://www.kennet.com/who-we-are/"),
            ("Lullabot", "https://www.lullabot.com/about"),
            ("Hanno", "https://hanno.co/team/")
           ]

misc_is_a_category = False

# Each location is stored as a list because some locations can go by multipe names
desired_locations = list()
if misc_is_a_category:
    desired_locations.append(["Silicon Valley", "Bay Area", "San Francisco", "Mountain View", "Oakland, CA"])
    desired_locations.append(["London", "London, United Kingdom"])
########################################################

'''
This function is only used if the user specifies their own locations (when misc_is_a_category == True)
'''
def categorize(location, locations):
    for i in range(len(locations)):
        if location in locations[i]:
            return locations[i][0]
    return "Misc"

'''
This function isn't necessary to understand.  It just formats the website data into
something the D3 visualization tree can read.
'''
def organize(location_to_person_map):
    ret = []
    for location in location_to_person_map.keys():
        children = []
        for child in location_to_person_map[location]:
            children.append({"name": child})
        ret.append({ "name": location, "children": children })
    return ret

'''
If more websites are desired, all that's needed are two functions that can point
to each members' name and location
'''
def find_locations_Kennet(page):
    return page.findAll("span", {"class": "location"})

def find_name_Kennet(page_location):
    return page_location.parent.find("img")['alt']

def find_locations_Lullabot(page):
    locations = page.findAll("div", {"class": "layout-who-we-are__staff__member-hover__location"})
    return locations

def find_name_Lullabot(page_location):
    return page_location.parent.parent.find("div", {"class": "headshot-square__title"}).text
  
def find_locations_Hanno(page):
    info = page.findAll("div", {"class": "card__image-overlay__content"})
    locations = [elem.find("p") for elem in info]
    return locations

def find_name_Hanno(page_location):
    return page_location.parent.find("h3").text

'''
Once the unique finder is made for a website, the function names should
be added here
'''    
def get_finders(website_name):
    if(website_name == "Kennet"):
        return find_locations_Kennet, find_name_Kennet
    elif(website_name == "Lullabot"):
        return find_locations_Lullabot, find_name_Lullabot
    elif(website_name == "Hanno"):
        return find_locations_Hanno, find_name_Hanno
    
'''
This is a helper function for the main function "scrape".  This function returns an organized map of 
members' names and locations for one particular website.  It needs to be fed the parameter "page_locations"
which is a list of every member's location.  A member's name and location are found because these are 
generally placed in the same HTML div and "page_locations" is a list of pointers that target near this div.
'''
def get_location_person_map(page_locations, name_finder, misc_is_a_category, desired_locations):
    location_to_person_map = {}
    for page_location in page_locations:
        member_name = name_finder(page_location)
        page_location = page_location.text
        
        if(misc_is_a_category):
            member_location = categorize(page_location, desired_locations)
        else:
            member_location = page_location 
        
        if(member_location not in location_to_person_map):
            location_to_person_map[member_location] = []
            
        location_to_person_map[member_location].append(member_name)
        
    return organize(location_to_person_map)

'''
This is the main function.  It loops through each website, pulls its data, and then orgainizes it. 
'''
def scrape(websites, misc_is_a_category, desired_locations):
    
    # It looks strange, but the data needs to be stored in this way for the visualization tree to read it properly
    master_json = {"name": "Investors", "children" :[]}
    for i in range(len(websites)):
        print("Fetching data from website #{} out of {} total webites".format(str(i + 1), str(len(websites))))
        website = websites[i]
        location_finder, name_finder = get_finders(website[0])

        # Retrieve the website data from the url
        page = urllib.request.urlopen(website[1])
        page = BeautifulSoup(page)

        # Get all the profiles' locations
        page_locations = location_finder(page)

        # Sets up a map that lists the names with the locations
        location_to_person_map = get_location_person_map(page_locations, name_finder, misc_is_a_category, desired_locations)
        
        ret = {"name": website[0], "children": location_to_person_map}
        master_json["children"].append(ret)
    return master_json
   
# This hash contains all of the webpages relevant information 
master_json = scrape(websites, misc_is_a_category, desired_locations)
    
# Saves everything to a json file so the tree will work properly
import json
with open('tree_files/investors.json', 'w') as scraped_data_file:
    json.dump(master_json, scraped_data_file)
    
# Opens the D3 tree html file (new=2 means the page will be opened in a new tab)
import webbrowser
import os
webbrowser.open('file://' + os.path.realpath('tree_files/tree.html'), new=2)
