# ERModel-to-RelationalModel
A tool to transform ER diagram components into a Relational Schema


## How do I get set up? ##

### Building the project ###

From the current directory (you should be able to see ERModeltoRelationalModel/),
use the following commands:

    cd ERModeltoRelationalModel/
    ant jar

to build the project and create a .jar file.
To run the executable, use

    cd dist/
    java -jar ERModeltoRelationalModel.jar

There are no options for the program, so the above command will run the program exactly.

### How to use the program ###

After entering the previous command, a prompt will ask if you want to start a new model or load a file.
Select N or n to begin a new model and enter the name of the model when prompted.
Now, the program give several options:
  
    What would you like to do with Company?
    Add: Entity(E), Relationship(R), Attribute(A)
    Set Key Attribute(K)
    Convert(C)
    Display(D)
    Exit\Quit

  * When adding an Entity, you will be prompted for a name and to decide if it is a weak Entity.
    + You are also given the option to add attributes and set the primary key.
  * When adding a Relationship, you will be prompted for a name and information for both Entities involved.
    + The Entities need to be added BEFORE a relationship is established between them!
    + Information includes: Name, participation, and cardinality.
    + Attributes can be added in this step as well.
  * Attributes can be added to any previously added Entities or Relationships.
  * The Primary Key for an Entity can be changed at any time.
  * The current model can be converted into a Relational model at any time.
  * To display the current Entity-Relationship model, the display option is available.
  * Use exit or quit to stop the program (e or q are not viable options).
    + When quitting, you will be prompted to save the model
    + If you decide to save the model, it will be stored in the ./model directory as a .dat file
  
### Available Models ###

Provided in the model directory are the following files for testing the code with full models:

  + Company.dat - a model of a company with employees, departments, and projects
  + Ship_Tracking.dat - a model of tracking ships with ports, ship types, and locations
  
To use these files, enter F or f when the first prompt appears and enter the appropriate file name.

### Making a New Model in a File ###

Start in the ./model directory.
To make a model with a file, give it the name you want (generally, the name of the model is used).
The extension can be any text file.
When the model is saved with the program, it will be saved as a .dat file.
In general, enter entities before relationships.
Doing this will ensure that the program works properly.
Here is the format for regular entities, weak entities, and relationships:

Regular Entity:
* Start with ER
* Add the name of the entity
* Add "A>"
* Enter all attributes with the format
    + If it is not apart of a composite attribute, {attribute name}:{type}
    + If it is apart of a composite attribute, {composite parent}>{attribute name}:{type}
* Add "<A"
* Add "K>"
* Enter the attributes that make up the key, each on a separate line
* Add "<K"

        ER
        Department
        A>
        Number:SIMPLE
        Name:SIMPLE
        Location:MULTIVALUED
        Number_of_employees:DERIVED
        <A
        K>
        Number
        Name
        <K

Weak Entity:
* Start with EW
* Add the name of the entity
* Add the name of the identifying relationship
* Add "A>"
* Enter all attributes with the format
    + If it is not apart of a composite attribute, {attribute name}:{type}
    + If it is apart of a composite attribute, {composite parent}>{attribute name}:{type}
* Add "<A"
* Add "K>"
* Enter the attributes that make up the key, each on a separate line
* Add "<K"
    
        EW
        Dependent
        Dependents_Of
        A>
        Name:SIMPLE
        Sex:SIMPLE
        Birth_date:SIMPLE
        Relationship:SIMPLE
        <A
        K>
        Name
        <K
        
Relationship:
* Start with R
* Add the name of the relaionship
* Add the first entity
* Add the entity's participation
* Add the entity's cardinality
* Add the second entity
* Add the entity's participation
* Add the entity's cardinality
* Add "A>"
* Enter all attributes with the format
    + If it is not apart of a composite attribute, {attribute name}:{type}
    + If it is apart of a composite attribute, {composite parent}>{attribute name}:{type}
* Add "<A"

        R
        Works_On
        Employee
        FULL
        M
        Project
        FULL
        N
        A>
        Hours:SIMPLE
        <A

At the end of the file, enter "EOF".
For reference, look at either Company.dat or Ship_Tracking.dat in the ./model directory.
    
## Who do I talk to? ##

* Jordan Caldwell at University of Rochester
    + NetId: jcaldwe8
    + Email: jcaldwe8@ur.rochester.edu
