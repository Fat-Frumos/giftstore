import requests
from bs4 import BeautifulSoup
import webbrowser

response = requests.get('http://localhost:8080/api/certificates')

soup = BeautifulSoup(response.content, 'html.parser')

links = soup.find_all('a')

for link in links:
    href = link.get('href')
    print(href)

    webbrowser.open_new_tab(href)
