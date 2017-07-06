


### Some instructions

#### Create the database
##### Unix
```psql -U postgres postgres -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\0.database.sql```

##### Windows
```psql -U postgres -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\0.database.sql postgres```


#### Create the schema

```psql -h localhost -d runkeeper -U postgres -p 5432 -a -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\1.schema.sql```

#### Create the tables 

```psql -h localhost -d runkeeper -U postgres -p 5432 -a -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\2.CommentsJobs.sql```
