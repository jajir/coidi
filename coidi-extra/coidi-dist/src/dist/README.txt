This is distribution package for coidi project. For more detail about project see http://sourceforge.net/projects/coidi/

# About this document

Here will be described first steps with coidi OpenID implementation.

# About this package
This package contains testing OpenID provider and testing relaying party.
Both are stand alone web applications. 

# How to start

Open  shell terminal and run:

./coidi.sh start

# verify that it's running

1) Check that there are no exception in log files in directory log
2) try to access testing OpenID at: provider http://localhost:8080
   try to access relaying party at: http://localhost:8081
   If both are running than both started correctly
   
# first steps
Access relaying party and try to login. In lower part of page are possible user OpenID identifies that could be used for main main login form at same page.
Please remember name and password associated with identifier.
When you submit form you are redirected to OpenID provider and you could use credentials from previous step.
After successful login you are redirected back to relaying party.

# next steps
for more information about testing please visit project page at http://sourceforge.net/projects/coidi/

 