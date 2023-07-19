
# GIS2023
Provincial water

An Italian province, with a large mountain area, wants to create an information system for hydrography management.

## Content of the repo
```bash
├── README.md
├── app_src
│   ├── database -> sql file with the database schema
│   ├── openjmp_plug -> openjump plugin code
│   └── web_app ->w eb app code
└── tex_src -> report 
```

## Live demo

A live demo can be found here:
[Link](https://gis2023.bitsei.it)

## How to run

### Web server 

Use apache web server to run the php application, just put the `web_app` folder inside the `htdocs` and go to `localhost://web_app`

### Openjump plugin

Create a package `openjmp_plug` and put inside all the content found in `openjmp_plug`.
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

