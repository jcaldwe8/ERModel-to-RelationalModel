# ERModel-to-RelationalModel
A tool to transform ER diagram components into a Relational Schema


### How do I get set up? ###

## Building the project ##

From the current directory (you should be able to see ERModeltoRelationalModel/),
use the following commands:

    cd ERModeltoRelationalModel/
    ant jar

to build the project and create a .jar file.
To run the executable, use

    cd dist/
    java -jar ERModeltoRelationalModel.jar

There are no options for the program, so the above command will run the program exactly.

## How to use the program ##

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
    + If you decide to save the model, it will be stored in the ./test directory
  
## How to run tests ##

Provided in the test directory are the following files for testing:

  + Company.dat
  
To use these files, enter F or f when the first prompt appears and enter the appropriate file name.

### Who do I talk to? ###

* Jordan Caldwell at University of Rochester - jcaldwe8
