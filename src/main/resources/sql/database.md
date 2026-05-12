**Practical information about the Database**

The database is running in PostgreSQL and is administered via pgAdmin.
The database is called ProjektFOG and contains 6 tables.

**Table 1: users**
Contains all registered users in the system.

**Table 2: contact_information**
Contains contact information for both registered users and guest users.
This is used when an order is created, so salespersons and admins are able to get in contact with the buyer.

**Table 3: material**
This is a catalog of all registered materials.

**Table 4: orders**
This table contains information about who bought what.
An order is only created when a user or guest has sent an inquiry with the correct configuration (dimensions).

**Table 5: carport**
Contains the dimensions and configuration of the carport.

**Table 6: order_line**
Contains information about what materials are needed and in what quantity.