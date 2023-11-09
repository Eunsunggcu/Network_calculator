## Getting Started

This project, which consists of java, is part of a network class in the Department of Software at Gachon University.

When the client requests a four-pronged operation (two maximum terms), the server will refer to the server config class to perform the operation and send the result back to the defined protocolfh client. The client then outputs the meaning of the protocol as a log.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib` - CalClientEx.java, CalServerEx.java, Serverconfig.java


> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Protocol meaning

The previous string of the protocol defines the type of protocol.
Ex: ERR_001 : It means this protocol is error protocol.

1.	Error protocol
-	ERR_001 : Error: Too many operands.
-	ERR_002 : Error: Not enough operands.
-	ERR_003 : Error: Division by zero.
-	ERR_004 : Error: Unknown operator.


2.	Answer procol
-	ANS_000 : This protocol means that the result is zero


