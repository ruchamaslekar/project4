# Project 4 Webservers
For this project, you will write two webservers that provide a Restful API to access information about hotels and hotel reviews:

- A webserver (RawSocketServer) that uses raw sockets to communicate with clients
- A webserver (JettyServer) that uses Jetty/servlets

You need to make servers as general as possible, but the servers only need to handle GET requests.

You will also write additional classes - handlers/servlets. These are classes that are specific to the project, and will process requests coming to the specific end points.
For example, for the  JettyServer, you will write a HotelServlet that handles requests to /hotelInfo.
You will write other servlets for other ends points.

Requirements for each of the two servers and the API are described in the pdf of Canvas.
