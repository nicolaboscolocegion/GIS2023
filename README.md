
# GIS2023
UNIPD - Computer Engineering - Geographic Information System course:

### Provincial water

An Italian province, with a large mountain area, wants to create an information system for hydrography management.

## Content of the repo
```
├── README.md
├── app_src
│   ├── database -> sql file with the database data and schema
│   ├── javadoc -> documentation for openJUMP plugin 
│   ├── openjmp_plug -> openJUMP plugin code
│   └── web_app -> web app code
└── tex_src -> report 
```

## Live demo

A live demo can be found here:
[Link](https://gis2023.bitsei.it)

## How to run

### Web app

- Use [PostgreSQL](https://www.postgresql.org/) and install [PostGIS](http://postgis.net/),
- Import the database schema and data with the file found in: `app_src/database/database.sql`


- Use apache web server to run the php application, just put the `web_app` folder inside the `htdocs` folder.

- For the connection with the database and your web app you must change the username, password and DB name that you are using, in the [app_src/web_app/conndb.php](app_src/web_app/conndb.php):

```php 
$servername = "<Your IP server or localhost>";
$username = "<Your username>";
$password = "<Your Password>";
$dbname = "<Your database name>";
```

- Now the web app is ready, go to `localhost://web_app`


### OpenJUMP plugin

- In your openJUMP environment create a package `openjmp_plug` and put inside all the class found in `openjmp_plug`.
- In the class `Database.java` there is a variable called `databaseUrl` in line 30, change it to point your database. 
- Then add the plugin in your `plugins.xml` and add the plugin reference:
```xml 
<workbanch>
    ...
    <plug-in>openjmp_plug.Plug</plug-in>
    ...
</workbanch>
```
- Then run openJUMP with the right options inside your IDE

## How to use it

You can find the complete project report with a guide for how to use it here: [report](tex_src/main.pdf)

## Credits

Web app Bootstrap template: [bootstrap-italia-playground](https://github.com/italia/bootstrap-italia-playground/tree/main)
