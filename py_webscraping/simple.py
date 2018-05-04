# Removes warnings that occassionally show in imports
import warnings
warnings.filterwarnings('ignore')

# Libraries for website scraping
from bs4 import BeautifulSoup
import requests
import urllib

# URL of investment team information
website = "https://www.kennet.com/who-we-are/"

# Retrieving website
page = urllib.request.urlopen(website)
page = BeautifulSoup(page)

# This will keep track of all the team members in either group
location_to_person_map = {"London": [], "Silicon Valley": []}

# The location elements are labeled with the class location
locations = page.findAll("span", {"class": "location"})

for location in locations:
	# The name can be found by the alt tag for the image.
    member_name = location.parent.find("img")['alt']
    
    # Places the name with the correct location
    if location.text == "London":
        location_to_person_map["London"].append(member_name)
    elif location.text == "Silicon Valley":
        location_to_person_map['Silicon Valley'].append(member_name)
    
# Printing answers
print()
print("Answer".center(70, " "))
print("".center(75, "-"))
print("The London members are: " + ", ".join(location_to_person_map["London"]))
print()
print("The Silicon Valley members are: " + ", ".join(location_to_person_map["Silicon Valley"]))
print()

