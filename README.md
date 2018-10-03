
![](https://github.com/architkhullar/Project5/blob/master/Screenshots/Screen%20Shot%202018-10-03%20at%206.58.07%20PM.png)
![](https://github.com/architkhullar/Project5/blob/master/Screenshots/Screen%20Shot%202018-10-03%20at%206.58.16%20PM.png)
1. API Requirements:

a) You are provided with a “discount.json” file, which includes the list of items that
are discounted in the different regions.

b) Create the required DB schema to store and manage the product discount
information. Import the provided JSON file as part of your DB schema.

c) Import the provided photo files so that they are accessible through the server.

d) Setup your server to provide the an API that enables the fetching of discounted
products for a specific region.

2. Application Requirements:

a) The application consists of a single window that enables the user to view the
discounted items around him while shopping in the store.

b) Create a new application, download and setup the Estimote SDK.

c) Figure 2 shows the regions names, and the beacon UUID, major and minor
information. Please note that the provided discount information provides a region
attribute, which describes the region that a specific product is located.

d) When the user is in the store, the app should locate the closest beacon and
present only the products for the region belonging to the closest beacon. The list
is presented in Figure 1(b).

e) As the user moves the app should contact the api to get the list of discounted
items in the closest region, and the list should be refreshed to show the retrieved
list of discounted products for the closest region.

f) Your application should avoid oscillating between regions, which is when the app
during a scan assumes region 1 then in the next scan assumes region 2, and
then region 1. This case might happen when the user is equidistant from multiple
beacons or due to errors in the distance estimations. This will affect the user
experience and your app should present a usable solution to this problem.
g) If the app is unable to locate any beacons it should display all the discounted
products sorted by region.

3. Report and Presentation Requirements:

a) Your report should describe the DB Schema used, and API descriptions.

b) Screenshot of the app when visiting the different regions.

c) Describe the algorithm used to avoid oscillating between regions. 
