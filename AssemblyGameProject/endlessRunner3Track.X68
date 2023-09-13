*-----------------------------------------------------------
* Title      : Endless Runner
* Written by : Seán Rourke
* Known Issues: Game is quite cpu intensive. Can lead to the game running slower and inputs not being read.
* Worse verion of the game is also included, which runs better.
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

*-----------------------------------------------------------
* Section       : Trap Codes
* Description   : Trap Codes used throughout StarterKit
*-----------------------------------------------------------
* Trap CODES
TC_SCREEN   EQU         33          ; Screen size information trap code
TC_S_SIZE   EQU         00          ; Places 0 in D1.L to retrieve Screen width and height in D1.L
                                    ; First 16 bit Word is screen Width and Second 16 bits is screen Height
TC_KEYCODE  EQU         19          ; Check for pressed keys
TC_DBL_BUF  EQU         92          ; Double Buffer Screen Trap Code
TC_CURSR_P  EQU         11          ; Trap code cursor position

TC_EXIT     EQU         09          ; Exit Trapcode

*-----------------------------------------------------------
* Section       : Charater Setup
* Description   : Size of Player and Enemy and properties
* of these characters e.g Starting Positions and Sizes
*-----------------------------------------------------------
PLYR_W_INIT EQU         30          ; Players initial Width
PLYR_H_INIT EQU         30          ; Players initial Height
ENMY_W_INIT EQU         30          ; Enemy initial Width
ENMY_H_INIT EQU         30          ; Enemy initial Height
ENMY2_W_INIT EQU        30          ; Enemy2 initial Width
ENMY2_H_INIT EQU        30          ; Enemy2 initial Height
POINTS      EQU         01          ; Points added

*-----------------------------------------------------------
* Section       : Keyboard Keys
* Description   : Spacebar and Escape or two functioning keys
* Spacebar to JUMP and Escape to Exit Game
*-----------------------------------------------------------
ESCAPE      EQU         $1B         ; Escape ASCII Keycode
UP          EQU         $57         ; W ASCII Keycode
DOWN        EQU         $53         ; S ASCII Keycode
MIDDLE      EQU         $44         ; D ASCII Keycode
ENTER       EQU         $0D         ; ENTER ASCII Keycode

*-----------------------------------------------------------
* Subroutine    : Start Menu
* Description   : Prompt User to start the game
*-----------------------------------------------------------
START_MENU:

    ; Clear the screen (see easy 68k help)
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
	MOVE.W  #$FF00,     D1          ; Fill Screen Clear
	TRAP	#15                     ; Trap (Perform action)
	
	MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0202,     D1          ; Col 02, Row 02
    TRAP    #15                     ; Trap (Perform action)
    LEA     WARNING1_MSG,  A1       ; WARNING Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0203,     D1          ; Col 02, Row 03
    TRAP    #15                     ; Trap (Perform action)
    LEA     WARNING2_MSG,  A1       ; WARNING Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0B0B,     D1          ; Col 11, Row 11
    TRAP    #15                     ; Trap (Perform action)
    LEA     TITLE_MSG,  A1          ; Title Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0B0D,     D1          ; Col 11, Row 13
    TRAP    #15                     ; Trap (Perform action)
    LEA     NAME_MSG,  A1           ; Name Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)

    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0B11,     D1          ; Col 11, Row 17
    TRAP    #15                     ; Trap (Perform action)
    LEA     UP_MSG,  A1             ; Up Control Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0B12,     D1          ; Col 11, Row 18
    TRAP    #15                     ; Trap (Perform action)
    LEA     MIDDLE_MSG,  A1         ; Middle Control Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0B13,     D1          ; Col 11, Row 19
    TRAP    #15                     ; Trap (Perform action)
    LEA     DOWN_MSG,  A1           ; Down Control Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)

    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0B0F,     D1          ; Col 11, Row 15
    TRAP    #15                     ; Trap (Perform action)
    LEA     START_MSG,  A1          ; Start Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
 
 LOOP:
    MOVE.B  #TC_KEYCODE,D0          ; Listen for Keys
    TRAP    #15                     ; Trap (Perform action)
    MOVE.L  D1,         CURRENT_KEY ; Put Current Key in Memory
    CMP.L   #ESCAPE,    CURRENT_KEY ; Is Current Key Escape
    BEQ     EXIT                    ; Exit if Escape
    TST.L   CURRENT_KEY             ; Is a key being pressed
    BEQ     LOOP                    ; Loop for input
    BSR     INITIALISE              ; Start game
*-----------------------------------------------------------
* Subroutine    : Initialise
* Description   : Initialise game data into memory such as 
* sounds and screen size
*-----------------------------------------------------------
INITIALISE:

    ; Screen Size
    MOVE.B  #TC_SCREEN, D0          ; access screen information
    MOVE.L  #TC_S_SIZE, D1          ; placing 0 in D1 triggers loading screen size information
    TRAP    #15                     ; interpret D0 and D1 for screen size
    MOVE.W  D1,         SCREEN_H    ; place screen height in memory location
    SWAP    D1                      ; Swap top and bottom word to retrive screen size
    MOVE.W  D1,         SCREEN_W    ; place screen width in memory location

    ; Place the Player at the center of the screen
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_W,   D1          ; Place Screen width in D1
    DIVU    #04,        D1          ; divide by 2 for center on X Axis
    MOVE.L  D1,         PLAYER_X    ; Players X Position

    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #2,         D1          ; divide by 2 for center on Y Axis
    MOVE.L  D1,         PLAYER_Y    ; Players Y Position

     ; Initial Position for Enemy
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_W,   D1          ; Place Screen width in D1
    MOVE.L  D1,         ENEMY_X     ; Enemy X Position

    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on Y Axis
    MOVE.L  D1,         ENEMY_Y     ; Enemy Y Position
    
     ; Initial Position for Enemy2
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_W,   D1          ; Place Screen width in D1
    MOVE.L  D1,         ENEMY2_X    ; Enemy2 X Position

    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on Y Axis
    MOVE.L  D1,         ENEMY2_Y    ; Enemy2 Y Position


    MOVE.L  #0, PLAYER_SCORE
    MOVE.L  #0, HIGH_SCORE

    ; Enable the screen back buffer(see easy 68k help)
	MOVE.B  #TC_DBL_BUF,D0          ; 92 Enables Double Buffer
    MOVE.B  #17,        D1          ; Combine Tasks
	TRAP	#15                     ; Trap (Perform action)

    ; Clear the screen (see easy 68k help)
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
	MOVE.W  #$FF00,     D1          ; Fill Screen Clear
	TRAP	#15                     ; Trap (Perform action)
	
*-----------------------------------------------------------
* Subroutine    : Game
* Description   : Game including main GameLoop. GameLoop is like
* a while loop in that it runs forever until interupted
* (Input, Update, Draw). The Enemies Run at Player Jump to Avoid
*-----------------------------------------------------------
GAME:
     

GAMELOOP:
    ; Main Gameloop
    BSR     INPUT                   ; Check Keyboard Input
    BSR     UPDATE                  ; Update positions and points
    BSR     CHECK_COLLISIONS        ; Check for Collisions
    BSR     DRAW                    ; Draw the Scene
    BRA     GAMELOOP                ; Loop back to GameLoop
    
*-----------------------------------------------------------
* Subroutine    : Input
* Description   : Process Keyboard Input
*-----------------------------------------------------------
INPUT:
    ; Process Input
    CLR.L   D1                      ; Clear Data Register
    MOVE.B  #TC_KEYCODE,D0          ; Listen for Keys
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  D1,         D2          ; Move last key D1 to D2
    CMP.B   #00,        D2          ; Key is pressed
    BEQ     PROCESS_INPUT           ; Process Key
    TRAP    #15                     ; Trap for Last Key
    ; Check if key still pressed
    CMP.B   #$FF,       D1          ; Is it still pressed
    BEQ     PROCESS_INPUT           ; Process Last Key
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Process Input
* Description   : Branch based on keys pressed
*-----------------------------------------------------------
PROCESS_INPUT:
    MOVE.L  D2,         CURRENT_KEY ; Put Current Key in Memory
    CMP.L   #ESCAPE,    CURRENT_KEY ; Is Current Key Escape
    BEQ     EXIT                    ; Exit if Escape
    CMP.L   #UP,  CURRENT_KEY       ; Is W current key
    BEQ     MOVE_UP                 ; Branch to move up
    CMP.L   #DOWN,  CURRENT_KEY     ; Is S current key
    BEQ     MOVE_DOWN               ; Branch to move down
    CMP.L   #MIDDLE,  CURRENT_KEY   ; Is D current key
    BEQ     MOVE_MIDDLE             ; Branch to move to the middle
    RTS                             ; Return to subroutine
    
*-----------------------------------------------------------
* Subroutine    : Movements
* Description   : Move character up and down
*----------------------------------------------------------- 
MOVE_UP:
    CLR.L   D1
    MOVE.W  SCREEN_H,   D1
    DIVU    #2,         D1
    SUB     #31,        D1      ; find destination of move up
    MOVE.L  PLAYER_Y,   D2
    CMP.L   D1,         D2      ; compare to player y
    BEQ     MOVE_DONE           ; don't move if already there
    BRA     PERFORM_UP          ; move if not there


PERFORM_UP:
    MOVE.L  PLAYER_Y,   D1
    SUB     #31,        D1
    MOVE.L  D1,         PLAYER_Y ; move player up
    RTS


MOVE_DOWN:
    CLR.L   D1
    MOVE.W  SCREEN_H,   D1
    DIVU    #2,         D1
    ADD     #31,        D1      ; find destination of move down
    MOVE.L  PLAYER_Y,   D2
    CMP.L   D1,         D2      ; compare to player y
    BEQ     MOVE_DONE           ; don't move if already there
    BRA     PERFORM_DOWN        ; move if not there
    
PERFORM_DOWN:
    MOVE.L  PLAYER_Y,   D1
    ADD     #31,        D1
    MOVE.L  D1,         PLAYER_Y    ; move player down
    RTS
    
MOVE_MIDDLE:
    CLR.L   D1
    MOVE.W  SCREEN_H,   D1
    DIVU    #2,         D1
    MOVE.L  D1,         PLAYER_Y    ; place player in middle
    BRA     MOVE_DONE
   
MOVE_DONE:
    RTS

UPDATE:
    ; Move the Enemy
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.L  ENEMY_X,    D1          ; Move the Enemy X Position to D0
    CMP.L   #00,        D1
    BLE     RESET_ENEMY_POSITION    ; Reset Enemy if off Screen
    BRA     MOVE_ENEMY              ; Move the Enemy

    RTS                             ; Return to subroutine  

*-----------------------------------------------------------
* Subroutine    : Move Enemy
* Description   : Move Enemy Right to Left
*-----------------------------------------------------------
MOVE_ENEMY:
    SUB.L   #01,        ENEMY_X     ; Move enemy by X Value
    SUB.L   #01,        ENEMY2_X
    RTS

*-----------------------------------------------------------
* Subroutine    : Reset Enemy
* Description   : Reset Enemy if to passes 0 to Right of Screen
*-----------------------------------------------------------
RESET_ENEMY_POSITION:
    CLR.L   D1
    ADD.L   #POINTS,    D1          ; Move points upgrade to D1 for each enemy avoided
    ADD.L   PLAYER_SCORE,D1         ; Add to current player score
    MOVE.L  D1, PLAYER_SCORE        ; Update player score in memory

    ; Check if the current score is higher than the high score
    MOVE.L  HIGH_SCORE, D2
    CMP.L   D2, D1
    BHI     UPDATE_HIGH_SCORE

    ; If the current score is not higher, clear D1 and update enemy position
    CLR.L   D1
    MOVE.W  SCREEN_W, D1
    MOVE.L  D1, ENEMY_X
    MOVE.L  D1, ENEMY2_X
    BRA     RANDOM_NUMBER
    RTS

UPDATE_HIGH_SCORE:
    ; Update the high score and reset D1
    MOVE.L  PLAYER_SCORE, HIGH_SCORE
    CLR.L   D1
    MOVE.W  SCREEN_W, D1
    MOVE.L  D1, ENEMY_X
    MOVE.L  D1, ENEMY2_X
    BRA     RANDOM_NUMBER
    RTS 

    
   
RANDOM_NUMBER:
    CLR.L   D1
    CLR.L   D2
    MOVE.B  #8,         D0          ; get time
    TRAP    #15
    AND.L   #$0000FFFF, D1          ; prevent overflow
    DIVU    #3,         D1          ; get random number of three options
    SWAP    D1
    ADDQ.W  #1,         D1          ; minimum 1
    MOVE.W  D1,         D2
    CLR.L   D1
    MOVE.W  D2,         D1
    CMP     #1,         D1          ; if number is 1, enemy on top track
    BEQ     ENEMY_UP
    CMP     #2,         D1          ; if number is 2, enemy on bottom track
    BEQ     ENEMY_DOWN
    RTS                             ; if number is neither, enemy on middle track
    
RANDOM_NUMBER2:                     ; same process again
    CLR.L   D1
    CLR.L   D2
    MOVE.B  #8,         D0
    TRAP    #15
    AND.L   #$0000FFFF, D1
    DIVU    #3,         D1
    SWAP    D1
    ADDQ.W  #1,         D1
    MOVE.W  D1,         D2
    CLR.L   D1
    MOVE.W  D2,         D1
    CMP     #1,         D1
    BEQ     ENEMY2_UP
    CMP     #2,         D1      
    BEQ     ENEMY2_DOWN
    RTS

; same processes as moving player
    
ENEMY_UP:                   
    CLR.L   D3
    MOVE.W  SCREEN_H,   D3
    DIVU    #2,         D3
    SUB     #31,        D3
    MOVE.L  ENEMY_Y,    D4
    CMP.L   D3,         D4
    BEQ     ENEMY_MOVE_DONE
    BRA     PERFORM_ENEMY_UP


PERFORM_ENEMY_UP:
    MOVE.L  ENEMY_Y,    D4
    SUB     #31,        D4
    MOVE.L  D4,         ENEMY_Y
    CLR.L   D4
    RTS
    
ENEMY2_UP:
    CLR.L   D3
    MOVE.W  SCREEN_H,   D3
    DIVU    #2,         D3
    SUB     #31,        D3
    MOVE.L  ENEMY2_Y,    D4
    CMP.L   D3,         D4
    BEQ     MOVE_DONE
    BRA     PERFORM_ENEMY2_UP


PERFORM_ENEMY2_UP:
    MOVE.L  ENEMY2_Y,    D4
    SUB     #31,        D4
    MOVE.L  D4,         ENEMY2_Y
    CLR.L   D4
    RTS

ENEMY_DOWN:
    CLR.L   D3
    MOVE.W  SCREEN_H,   D3
    DIVU    #2,         D3
    ADD     #31,        D3
    MOVE.L  ENEMY_Y,    D4
    CMP.L   D3,         D4
    BEQ     ENEMY_MOVE_DONE
    BRA     PERFORM_ENEMY_DOWN
    
PERFORM_ENEMY_DOWN:
    MOVE.L  ENEMY_Y,    D4
    ADD     #31,        D4
    MOVE.L  D4,         ENEMY_Y
    CLR.L   D4
    RTS
    
ENEMY2_DOWN:
    CLR.L   D3
    MOVE.W  SCREEN_H,   D3
    DIVU    #2,         D3
    ADD     #31,        D3
    MOVE.L  ENEMY2_Y,    D4
    CMP.L   D3,         D4
    BEQ     MOVE_DONE
    BRA     PERFORM_ENEMY2_DOWN
    
PERFORM_ENEMY2_DOWN:
    MOVE.L  ENEMY2_Y,    D4
    ADD     #31,        D4
    MOVE.L  D4,         ENEMY2_Y
    CLR.L   D4
    RTS
    
ENEMY_MOVE_DONE:
    BRA     RANDOM_NUMBER2      ; get second number when first has position decided
    BRA     MOVE_DONE


*-----------------------------------------------------------
* Subroutine    : Draw
* Description   : Draw Screen
*-----------------------------------------------------------
DRAW: 
    ; Enable back buffer
    MOVE.B  #94,        D0
    TRAP    #15

    ; Clear the screen
    MOVE.B	#TC_CURSR_P,D0          ; Set Cursor Position
	MOVE.W	#$FF00,     D1          ; Clear contents
	TRAP    #15                     ; Trap (Perform action)

    BSR     DRAW_LINES              ; draw borders
    BSR     DRAW_PLAYER             ; Draw Player
    BSR     DRAW_ENEMY              ; Draw Enemy
    BSR     DRAW_ENEMY2             ; draw enemy2
    BSR     DRAW_MESSAGES           ; draw messages
    
    RTS                             ; Return to subroutine
    


DRAW_MESSAGES:
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)

    ; Player Score Message
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0205,     D1          ; Col 02, Row 05
    TRAP    #15                     ; Trap (Perform action)
    LEA     SCORE_MSG,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    ; Player Score Value
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0905,     D1          ; Col 09, Row 05
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  #03,        D0          ; Display number at D1.L
    MOVE.L  PLAYER_SCORE,D1         ; Move Score to D1.L
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0201,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     UP_MSG,  A1             ; Up control Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0206,     D1          ; Col 02, Row 05
    TRAP    #15                     ; Trap (Perform action)
    LEA     HIGHSCORE_MSG,  A1      ; High Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0D06,     D1          ; Col 09, Row 05
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  #03,        D0          ; Display number at D1.L
    MOVE.L  HIGH_SCORE,D1           ; Move High Score to D1.L
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0202,     D1          ; Col 02, Row 02
    TRAP    #15                     ; Trap (Perform action)
    LEA     MIDDLE_MSG,  A1         ; Middle control Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$0203,     D1          ; Col 02, Row 05
    TRAP    #15                     ; Trap (Perform action)
    LEA     DOWN_MSG,  A1           ; Down control Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    RTS
*-----------------------------------------------------------
* Subroutine    : Draw Player
* Description   : Draw Player Square
*-----------------------------------------------------------
DRAW_PLAYER:
    ; Set Pixel Colors
    MOVE.L  #RED,     D1          ; Set Background color
    MOVE.B  #80,        D0          ; Task for Background Color
    TRAP    #15                     ; Trap (Perform action)

    ; Set X, Y, Width and Height
    MOVE.L  PLAYER_X,   D1          ; X
    MOVE.L  PLAYER_Y,   D2          ; Y
    MOVE.L  PLAYER_X,   D3
    ADD.L   #PLYR_W_INIT,   D3      ; Width
    MOVE.L  PLAYER_Y,   D4 
    ADD.L   #PLYR_H_INIT,   D4      ; Height
    
    ; Draw Player
    MOVE.B  #87,        D0          ; Draw Player
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Draw Enemy
* Description   : Draw Enemy Square
*-----------------------------------------------------------
DRAW_ENEMY:
    ; Set Pixel Colors
    MOVE.L  #WHITE,     D1          ; Set Background color
    MOVE.B  #80,        D0          ; Task for Background Color
    TRAP    #15                     ; Trap (Perform action)

    ; Set X, Y, Width and Height
    MOVE.L  ENEMY_X,    D1          ; X
    MOVE.L  ENEMY_Y,    D2          ; Y
    MOVE.L  ENEMY_X,    D3
    ADD.L   #ENMY_W_INIT,   D3      ; Width
    MOVE.L  ENEMY_Y,        D4 
    ADD.L   #ENMY_H_INIT,   D4      ; Height
    
    ; Draw Enemy    
    MOVE.B  #87,        D0          ; Draw Enemy
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine
    
DRAW_ENEMY2:
    ; Set Pixel Colors
    MOVE.L  #WHITE,     D1          ; Set Background color
    MOVE.B  #80,        D0          ; Task for Background Color
    TRAP    #15                     ; Trap (Perform action)

    ; Set X, Y, Width and Height
    MOVE.L  ENEMY2_X,    D1          ; X
    MOVE.L  ENEMY2_Y,    D2          ; Y
    MOVE.L  ENEMY2_X,    D3
    ADD.L   #ENMY2_W_INIT,   D3      ; Width
    MOVE.L  ENEMY2_Y,        D4 
    ADD.L   #ENMY2_H_INIT,   D4      ; Height
    
    ; Draw Enemy    
    MOVE.B  #87,        D0          ; Draw Enemy
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Draw Line
* Description   : Draw Lines
*-----------------------------------------------------------
DRAW_LINES:
    ; Set X, Y, Width and Height
    MOVE.W  SCREEN_W,   D1          ; right edge
    MOVE.W  SCREEN_H,   D2          ; Y
    DIVU    #2,         D2          ; centre
    SUB     #33,        D2          ; top line
    MOVE.L  #0,         D3          ; left edge
    MOVE.L  D2,         D4  
    MOVE.B  #84,        D0          ; draw line 
    TRAP    #15  

    ADD     #97,        D2          ; bottom line
    MOVE.L  #0,         D3
    MOVE.L  D2,         D4  
    MOVE.B  #84,        D0          
    TRAP    #15    
    RTS              


*-----------------------------------------------------------
* Subroutine    : Collision Check
* Description   : Axis-Aligned Bounding Box Collision Detection
* Algorithm checks for overlap on the 4 sides of the Player and 
* Enemy rectangles
* PLAYER_X <= ENEMY_X + ENEMY_W &&
* PLAYER_X + PLAYER_W >= ENEMY_X &&
* PLAYER_Y <= ENEMY_Y + ENEMY_H &&
* PLAYER_H + PLAYER_Y >= ENEMY_Y
*-----------------------------------------------------------
CHECK_COLLISIONS:
    CLR.L   D1                      ; Clear D1
    CLR.L   D2                      ; Clear D2
PLAYER_X_LTE_TO_ENEMY_X_PLUS_W:
    MOVE.L  PLAYER_X,   D1          ; Move Player X to D1
    MOVE.L  ENEMY_X,    D2          ; Move Enemy X to D2
    ADD.L   ENMY_W_INIT,D2          ; Set Enemy width X + Width
    CMP.L   D1,         D2          ; Do the Overlap ?
    BLE     PLAYER_X_PLUS_W_LTE_TO_ENEMY_X  ; Less than or Equal ?
    BRA     CHECK_COLLISIONS2    ; If not no collision
PLAYER_X_PLUS_W_LTE_TO_ENEMY_X:     ; Check player is not  
    ADD.L   PLYR_W_INIT,D1          ; Move Player Width to D1
    MOVE.L  ENEMY_X,    D2          ; Move Enemy X to D2
    CMP.L   D1,         D2          ; Do they OverLap ?
    BGE     PLAYER_Y_LTE_TO_ENEMY_Y_PLUS_H  ; Less than or Equal
    BRA     CHECK_COLLISIONS2    ; If not no collision   
PLAYER_Y_LTE_TO_ENEMY_Y_PLUS_H:     
    MOVE.L  PLAYER_Y,   D1          ; Move Player Y to D1
    MOVE.L  ENEMY_Y,    D2          ; Move Enemy Y to D2
    ADD.L   ENMY_H_INIT,D2          ; Set Enemy Height to D2
    CMP.L   D1,         D2          ; Do they Overlap ?
    BLE     PLAYER_Y_PLUS_H_LTE_TO_ENEMY_Y  ; Less than or Equal
    BRA     CHECK_COLLISIONS2    ; If not no collision 
PLAYER_Y_PLUS_H_LTE_TO_ENEMY_Y:     ; Less than or Equal ?
    ADD.L   PLYR_H_INIT,D1          ; Add Player Height to D1
    MOVE.L  ENEMY_Y,    D2          ; Move Enemy Height to D2  
    CMP.L   D1,         D2          ; Do they OverLap ?
    BGE     COLLISION               ; Collision !
    BRA     CHECK_COLLISIONS2    ; If not no collision
    
CHECK_COLLISIONS2:
    CLR.L   D1                      ; Clear D1
    CLR.L   D2                      ; Clear D2
PLAYER_X_LTE_TO_ENEMY2_X_PLUS_W:
    MOVE.L  PLAYER_X,   D1          ; Move Player X to D1
    MOVE.L  ENEMY2_X,    D2          ; Move Enemy X to D2
    ADD.L   ENMY2_W_INIT,D2          ; Set Enemy width X + Width
    CMP.L   D1,         D2          ; Do the Overlap ?
    BLE     PLAYER_X_PLUS_W_LTE_TO_ENEMY2_X  ; Less than or Equal ?
    BRA     COLLISION_CHECK_DONE    ; If not no collision
PLAYER_X_PLUS_W_LTE_TO_ENEMY2_X:     ; Check player is not  
    ADD.L   PLYR_W_INIT,D1          ; Move Player Width to D1
    MOVE.L  ENEMY2_X,    D2          ; Move Enemy X to D2
    CMP.L   D1,         D2          ; Do they OverLap ?
    BGE     PLAYER_Y_LTE_TO_ENEMY2_Y_PLUS_H  ; Less than or Equal
    BRA     COLLISION_CHECK_DONE    ; If not no collision   
PLAYER_Y_LTE_TO_ENEMY2_Y_PLUS_H:     
    MOVE.L  PLAYER_Y,   D1          ; Move Player Y to D1
    MOVE.L  ENEMY2_Y,    D2          ; Move Enemy Y to D2
    ADD.L   ENMY2_H_INIT,D2          ; Set Enemy Height to D2
    CMP.L   D1,         D2          ; Do they Overlap ?
    BLE     PLAYER_Y_PLUS_H_LTE_TO_ENEMY2_Y  ; Less than or Equal
    BRA     COLLISION_CHECK_DONE    ; If not no collision 
PLAYER_Y_PLUS_H_LTE_TO_ENEMY2_Y:     ; Less than or Equal ?
    ADD.L   PLYR_H_INIT,D1          ; Add Player Height to D1
    MOVE.L  ENEMY2_Y,    D2          ; Move Enemy Height to D2  
    CMP.L   D1,         D2          ; Do they OverLap ?
    BGE     COLLISION               ; Collision !
    BRA     COLLISION_CHECK_DONE    ; If not no collision

COLLISION_CHECK_DONE:               ; No Collision Update points   
    RTS                             ; Return to subroutine

COLLISION:
    MOVE.L  #00,   PLAYER_SCORE 
    BSR     GAMELOOP   
	
*-----------------------------------------------------------
* Subroutine    : EXIT
* Description   : Exit message and End Game
*-----------------------------------------------------------
EXIT:
    


* Put program code here

    SIMHALT             ; halt simulator
    
*-----------------------------------------------------------
* Section       : Graphic Colors
* Description   : Screen Pixel Color
*-----------------------------------------------------------
WHITE           EQU     $00FFFFFF
RED             EQU     $000000FF

*-----------------------------------------------------------
* Section       : Screen Size
* Description   : Screen Width and Height
*-----------------------------------------------------------
SCREEN_W        DS.W    01  ; Reserve Space for Screen Width
SCREEN_H        DS.W    01  ; Reserve Space for Screen Height

*-----------------------------------------------------------
* Section       : Keyboard Input
* Description   : Used for storing Keypresses
*-----------------------------------------------------------
CURRENT_KEY     DS.L    01  ; Reserve Space for Current Key Pressed

*-----------------------------------------------------------
* Section       : Character Positions
* Description   : Player and Enemy Position Memory Locations
*-----------------------------------------------------------
PLAYER_X        DS.L    01  ; Reserve Space for Player X Position
PLAYER_Y        DS.L    01  ; Reserve Space for Player Y Position
PLAYER_SCORE    DS.L    01  ; Reserve Space for Player Score
HIGH_SCORE      DS.L    01  ; Reserve Space for High Score

ENEMY_X         DS.L    01  ; Reserve Space for Enemy X Position
ENEMY_Y         DS.L    01  ; Reserve Space for Enemy Y Position
ENEMY2_X        DS.L    01
ENEMY2_Y        DS.L    01

*-----------------------------------------------------------
* Section       : Messages
* Description   : Messages to Print on Console, names should be
* self documenting
*-----------------------------------------------------------
EXIT_MSG        DC.B    'Exiting....', 0    ; Exit Message
SCORE_MSG       DC.B    'Score : ', 0       ; Score Message
UP_MSG          DC.B    'Move Up: W', 0
MIDDLE_MSG      DC.B    'Move Middle: D', 0
DOWN_MSG        DC.B    'Move Down: S',0
START_MSG       DC.B    'Press ENTER to Start', 0
TITLE_MSG       DC.B    'Endless Runner', 0
NAME_MSG        DC.B    'By Seán Rourke', 0
WARNING1_MSG    DC.B    'WARNING: This version is better but more processor intensive', 0
WARNING2_MSG    DC.B    'Can lead to inputs not registering', 0
HIGHSCORE_MSG   DC.B    'High Score: ', 0


* Put variables and constants here

    END    START        ; last line of source










*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
