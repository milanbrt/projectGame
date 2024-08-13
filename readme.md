| Bug behaviour                                                                                                                                                                               | How to reproduce                                                                                                      | Why it hasn't been fixed |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|--------------------------|
| When a player shoves a tiles to the right, and the treasure the player has to collect is directly next to him to the right the treasure will be collected without ever being on that tiles. | Go 1 tile to the left, right, up or down from the treasure you have to collect, then shove the direction to the tile. | Time constraints         |



# Fancy tokens documentation

## Constructor of Fancytokens
- Parameter: String key -> = the encryption key.
  In the Constructor of LabyrinthOpenApiBridge the key is created using: **new FancyTokens(UUID.randomUUID().toString()))** using **java.util.UUID**.
  This is a 36 characther string and is short for Universally Unique Identifier.

## Methods
### encrypt(String data)
Encrypts data using XOR cipher with provided key

XOR = Symmetric encyrption method --> Same key for encryption and decryption

In XOR encryption, each character in the plaintext is converted to its binary form using ASCII values, and the same is done for the key. Each pair of corresponding bits from the plaintext and the key is then combined using the XOR operation, which outputs 1 if the bits are different and 0 if they are the same. The resulting binary values are then converted back to characters to create the encrypted text.


- Parameter: String data = data to be ecrypted
- Returns: An encrypted String

### decrypt(String data)
The encryption method is symmetric so the decrypt methods just return the  encrypt methods.

The decrypt methods exists for clarity.

### createtoken(Labyrinth user)

The createToken method creates a token for a user by concatenating the user's gameID and playername with a colon in between, and then encrypting this string

- Parameter: The user
- Returns: The encyrpted token

### createUser(String token)
Does the opposite of createToken. It decrypts the token then splits it using the semicolon.

- Parameter: the encrypted token
- Returns: A new LabyrinthUser using the decrypted GameId and PlayerName .

### isValidPlayerName(String playerName)

Checks if the playername is valid and only contains letters from the alphabet

- Parameter: String playerName
- Returns: Boolean





    

