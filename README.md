<h1>
  Shared Signals Framework Receiver in Java
</h1>

A Java implementation of the OpenID Shared Signals Framework Reciever. 

<h2>
  Testing the receiver
</h2>

Utilizing the access token found in the Reciever.java file in pollEvents(), begin transmitting with the Transmitter found at <a href = "https://caep.dev/">caep.dev</a>.<br><br>
Run <a href = "src/main/java/Main.java">Main.java</a> to see the parsed SsfEvents that you transmit. 

Step 1: Pull the code using IntelliJ (build system not yet set up for command line).<br>
Step 2: Register with caep.dev to get an access token for transmitting. <br>
Step 3: In Receiver.java in pollEvents() edit the access token field to contian your unique access token gotten from caep.dev registration. <br>
Step 4: Transmit events using the caep.dev transmitter. <br>
Step 5: Run Main.java to see the parsed ssf evenst that you transmit. <br>

<br><br>
Todos:
1. Make command line build work.
2. Debug multiple event handling.  
