# Commands to create the runtime and package the installer

# Create runtime folder
jlink --module-path "C:\Program Files\Java\jdk-21\jmods" --add-modules java.base,java.desktop,java.sql --output runtime