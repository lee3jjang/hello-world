import requests as req
url = 'http://splash247.com/cimc-sinopacific-offshore-confirms-gas-carrier-resale-deal-pacific-gas/'
html = req.get(url).text
f = open('vessel_article.html', 'w')
f.write(html)
f.close()
