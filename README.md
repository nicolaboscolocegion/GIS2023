
# GIS2023
UNIPD project for Geographic Information System course:

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

Use [PostgreSQL](https://www.postgresql.org/) and install [PostGIS](http://postgis.net/),<br>
Import the database schema and data with the file found in: `app_src/database/database.sql`

Use apache web server to run the php application, just put the `web_app` folder inside the `htdocs` and go to `localhost://web_app`

### OpenJUMP plugin

In your openJUMP envoriement create a package `openjmp_plug` and put inside all the class found in `openjmp_plug`.
Then add the plugin in your `plugins.xml` and add the plugin reference:
```xml 
<workbanch>
    ...
    <plug-in>openjmp_plug.plug</plug-in>
    ...
</workbanch>
```
Then run openJUMP with the right options inside your IDE

## How to use it

You can find the complete project report with a guide for how to use it here: [report](tex_src/main.pdf)

## Credits

Web app Bootstrap template: [bootstrap-italia-playground](https://github.com/italia/bootstrap-italia-playground/tree/main)