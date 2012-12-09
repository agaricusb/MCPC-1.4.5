How to build MCPC jar
=====================
1. Make a bin folder where you build ur files from eclipse or build script.
2. Compile your latest changes.
3. Copy everything from bin into your craftbukkit 1.4.5R0.3 jar.
4. Delete the com/google folder from the jar.
5. Create a .bat file and add the following(assuming jar is named minecraft_server) :

java -Xms2048M -Xmx2048M -jar minecraft_server.jar -o true --nojline
PAUSE

6. Play!

Eclipse configuration
=====================
1. Import project
2. Right-click on MCPC-1.45-master, choose properties
3. Click Builders->Highlight compile jar->Edit
4. Change working directory to :

${workspace_loc:/MCPC-1.45-master/bin}

5. Change java location to yours. ex :

C:\Program Files\Java\jdk1.7.0_09\bin\jar.exe

6. Set arguments to (change name to anything):

cfM ../debug/mcpc-1.4.5-beta.jar *