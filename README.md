<h1>
  Shared Signals Framework Receiver in Java
</h1>

A Java implementation of the OpenID Shared Signals Framework Reciever. 

<h2>
  Testing the receiver
</h2>

Utilizing the access token found in the Reciever.java file in pollEvents(), begin transmitting with the Transmitter found at <a href = "https://caep.dev/">caep.dev</a>.<br><br>
Run <a href = "src/main/java/Main.java">Main.java</a> to see the parsed SsfEvents that you transmit. 

step 1: pull the code <br>
step 2: register with caep.dev to get an access token for transmitting <br>
step 3: in Receiver.java in pollEvents() edit the access token field to contian your unique access token gotten from caep.dev registration <br>
step 4: transmit events using the caep.dev transmitter <br>
step 5: run main.java to see the parsed ssf evenst that you transmit <br>
