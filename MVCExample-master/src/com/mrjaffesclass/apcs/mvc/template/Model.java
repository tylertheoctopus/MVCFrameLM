package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;
/**
 * The model represents the data that Land Mine uses.
 * @author Tyler Jackson
 * @version 2.3
 */
public class Model implements MessageHandler {
  // Messaging system for the MVC
  private final Messenger mvcMessaging;
  // Model's data variables
  private int variable1;
  private int variable2;
  private int[] mines = randomizeMines();
  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
  }
  /**
   * Initialize the model variables 
   * here and subscribe to the View's New Game button
   */
  public void init() {
    mvcMessaging.subscribe("view:changeButton", this);
    setVariable1(0);
    setVariable2(3);
  }
  /**
   * Extracting button number from View's message then checking
   * a mine is assigned to that button or not
   * @param messageName
   * @param messagePayload 
   */
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
    if (messagePayload != null) {
      System.out.println("MSG: received by model: "+messageName+" | "+messagePayload.toString());
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
    MessagePayload payload = (MessagePayload)messagePayload;
    int number = payload.getNumber();
    boolean mine = payload.getBoolean();
      if(mine == true)
      {
          if(number == mines[0] || number == mines[1] 
                  || number == mines[2] || number == mines[3] 
                  || number == mines[4] || number == mines[5]
                  || number == mines[6] || number == mines[7]
                  || number == mines[8] || number == mines[9]  )
          {
              setVariable2(getVariable2() - 1);
              mvcMessaging.notify("model:mine", number, true);    
          }
          else 
          {
          setVariable1(getVariable1() + 1);
          mvcMessaging.notify("model:noMine", number, true);
          }
      }
     }
  /**
   * Getter function for variable 1
   * @return Value of variable1
   */
  public int getVariable1() {
    return variable1;
  }
  /**
   * Setter function for variable 1
   * @param v New value of variable1
   */
  public void setVariable1(int v) {
    variable1 = v;
    // Once there is a new value to variable 1, a message will
    // be sent to the other modules
    if(variable2 >= 0)
    {
    mvcMessaging.notify("model:variable1Changed", variable1, true);
    }
  }
  /**
   * Getter function for variable2
   * @return Value of variable2
   */
  public int getVariable2() {
    return variable2;
  }
  /**
   * Setter function for variable2
   * @param v New value of variable2
   */
  public void setVariable2(int v) {
    variable2 = v;
    // Once there is a new value to variable 2, a message will
    // be sent to the other modules
         if(variable2 >= 0)
         {
             mvcMessaging.notify("model:variable2Changed", variable2, true);
         }   
    }
  /**
   * Method to randomize the placement
   * of the mines on the board
   * @return The location of each mine
   */
  public  int[] randomizeMines(){
      int[] a = new int[10]; 
        for ( int i = 0; i < 10; i++) 
         {
           a[i] = (int)(Math.random()*63);//note, this generates numbers from [0,9]
          for (int j = 0; j < i; j++) 
          {
               if (a[i] == a[j]) 
               {
                i--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                break;
               }
           }       
         }     
      return a;
    } 
}