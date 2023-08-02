/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.util.*;

// line 2 "model.ump"
public class TreasureChest
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreasureChest State Machines
  public enum ChestDoor { Locked, Unlocked }
  public enum ChestDoorUnlocked { Null, Closed, Opened }
  private ChestDoor chestDoor;
  private ChestDoorUnlocked chestDoorUnlocked;

  //Helper Variables
  private TimedEventHandler timeoutOpenedToClosedHandler;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreasureChest()
  {
    setChestDoorUnlocked(ChestDoorUnlocked.Null);
    setChestDoor(ChestDoor.Locked);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getChestDoorFullName()
  {
    String answer = chestDoor.toString();
    if (chestDoorUnlocked != ChestDoorUnlocked.Null) { answer += "." + chestDoorUnlocked.toString(); }
    return answer;
  }

  public ChestDoor getChestDoor()
  {
    return chestDoor;
  }

  public ChestDoorUnlocked getChestDoorUnlocked()
  {
    return chestDoorUnlocked;
  }

  public String chestStatus()
  {
    switch(chestDoor)
    {
      case Locked:
        return "locked";
      case Unlocked:
        switch(chestDoorUnlocked)
        {
          case Closed:
            return "closed";
          case Opened:
            return "open";
          default:
            return "";
        }
      default:
        return "";
    }
  }

  public boolean unlock()
  {
    boolean wasEventProcessed = false;
    
    ChestDoor aChestDoor = chestDoor;
    switch (aChestDoor)
    {
      case Locked:
        if (playerHasKey())
        {
          setChestDoorUnlocked(ChestDoorUnlocked.Closed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean forceLock()
  {
    boolean wasEventProcessed = false;
    
    ChestDoor aChestDoor = chestDoor;
    switch (aChestDoor)
    {
      case Unlocked:
        exitChestDoor();
        // line 101 "model.ump"
        report("Thud! The chest closes with a loud noise.");
              report("You lock the chest with your welding equipment.");
        setChestDoor(ChestDoor.Locked);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean lock()
  {
    boolean wasEventProcessed = false;
    
    ChestDoorUnlocked aChestDoorUnlocked = chestDoorUnlocked;
    switch (aChestDoorUnlocked)
    {
      case Closed:
        if (playerHasKey())
        {
          exitChestDoor();
        // line 71 "model.ump"
          report("You closed the lid of the chest.");
              	  report("You lock the chest with your key.");
          setChestDoor(ChestDoor.Locked);
          wasEventProcessed = true;
          break;
        }
        break;
      case Opened:
        if (playerHasKey())
        {
          exitChestDoor();
        // line 91 "model.ump"
          report("Thud! The chest closes with a loud noise.");
                report("You lock the chest with your key.");
          setChestDoor(ChestDoor.Locked);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean open()
  {
    boolean wasEventProcessed = false;
    
    ChestDoorUnlocked aChestDoorUnlocked = chestDoorUnlocked;
    switch (aChestDoorUnlocked)
    {
      case Closed:
        exitChestDoorUnlocked();
        // line 76 "model.ump"
        report("You hear a creaking sound.");
        setChestDoorUnlocked(ChestDoorUnlocked.Opened);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean timeoutOpenedToClosed()
  {
    boolean wasEventProcessed = false;
    
    ChestDoorUnlocked aChestDoorUnlocked = chestDoorUnlocked;
    switch (aChestDoorUnlocked)
    {
      case Opened:
        exitChestDoorUnlocked();
        // line 86 "model.ump"
        report("Thud! The chest closes with a loud noise.");
              	report("The chest just closed itself!");
        setChestDoorUnlocked(ChestDoorUnlocked.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean close()
  {
    boolean wasEventProcessed = false;
    
    ChestDoorUnlocked aChestDoorUnlocked = chestDoorUnlocked;
    switch (aChestDoorUnlocked)
    {
      case Opened:
        exitChestDoorUnlocked();
        // line 96 "model.ump"
        report("Thud! The chest closes with a loud noise.");
        setChestDoorUnlocked(ChestDoorUnlocked.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitChestDoor()
  {
    switch(chestDoor)
    {
      case Unlocked:
        exitChestDoorUnlocked();
        break;
    }
  }

  private void setChestDoor(ChestDoor aChestDoor)
  {
    chestDoor = aChestDoor;

    // entry actions and do activities
    switch(chestDoor)
    {
      case Unlocked:
        if (chestDoorUnlocked == ChestDoorUnlocked.Null) { setChestDoorUnlocked(ChestDoorUnlocked.Closed); }
        break;
    }
  }

  private void exitChestDoorUnlocked()
  {
    switch(chestDoorUnlocked)
    {
      case Closed:
        setChestDoorUnlocked(ChestDoorUnlocked.Null);
        break;
      case Opened:
        setChestDoorUnlocked(ChestDoorUnlocked.Null);
        stopTimeoutOpenedToClosedHandler();
        break;
    }
  }

  private void setChestDoorUnlocked(ChestDoorUnlocked aChestDoorUnlocked)
  {
    chestDoorUnlocked = aChestDoorUnlocked;
    if (chestDoor != ChestDoor.Unlocked && aChestDoorUnlocked != ChestDoorUnlocked.Null) { setChestDoor(ChestDoor.Unlocked); }

    // entry actions and do activities
    switch(chestDoorUnlocked)
    {
      case Opened:
        startTimeoutOpenedToClosedHandler();
        break;
    }
  }

  private void startTimeoutOpenedToClosedHandler()
  {
    timeoutOpenedToClosedHandler = new TimedEventHandler(this,"timeoutOpenedToClosed",2);
  }

  private void stopTimeoutOpenedToClosedHandler()
  {
    timeoutOpenedToClosedHandler.stop();
  }

  public static class TimedEventHandler extends TimerTask  
  {
    private TreasureChest controller;
    private String timeoutMethodName;
    private double howLongInSeconds;
    private Timer timer;
    
    public TimedEventHandler(TreasureChest aController, String aTimeoutMethodName, double aHowLongInSeconds)
    {
      controller = aController;
      timeoutMethodName = aTimeoutMethodName;
      howLongInSeconds = aHowLongInSeconds;
      timer = new Timer();
      timer.schedule(this, (long)howLongInSeconds*1000);
    }
    
    public void stop()
    {
      timer.cancel();
    }
    
    public void run ()
    {
      if ("timeoutOpenedToClosed".equals(timeoutMethodName))
      {
        boolean shouldRestart = !controller.timeoutOpenedToClosed();
        if (shouldRestart)
        {
          controller.startTimeoutOpenedToClosedHandler();
        }
        return;
      }
    }
  }

  public void delete()
  {}

  // line 5 "model.ump"
   public static  void main(String [] args){
    Thread.currentThread().setUncaughtExceptionHandler(new UmpleExceptionHandler());
    Thread.setDefaultUncaughtExceptionHandler(new UmpleExceptionHandler());
    TreasureChest chest = new TreasureChest();
    report(chest.getDescription());
    scenarioHeader("Unlock then open:");
    chest.unlock();
    report(chest.getDescription());
    chest.open();
    report(chest.getDescription());
    scenarioHeader("Lock from opened state:");
    chest.lock();
    report(chest.getDescription());
    scenarioHeader("Lock from closed state:");
    chest.unlock();
    chest.open();
    chest.close();
    chest.lock();
    report(chest.getDescription());
    scenarioHeader("Force lock from opened state:");
    chest.unlock();
    chest.open();
    chest.forceLock();
    report(chest.getDescription());
    
    scenarioHeader("Let the chest close itself:");
    chest.unlock();
    chest.open();
    report("You marvel at the contents of the old treasure chest...");
 try {
    Thread.sleep(4000); // wait for 4s, allowing the chest to close itself
 } catch (Exception e) {};
    report(chest.getDescription());
  }

  // line 37 "model.ump"
  public Boolean playerHasKey(){
    return true;
  }

  // line 41 "model.ump"
   static  void report(String message){
    System.out.println(message);
  }

  // line 45 "model.ump"
   static  void scenarioHeader(String message){
    report("\n"+message);
  	report("------------------------------"
           .substring(1,message.length()));
  }

  // line 51 "model.ump"
  public String getDescription(){
    return "The old treasure chest is " + chestStatus() + ".";
  }

  public static class UmpleExceptionHandler implements Thread.UncaughtExceptionHandler
  {
    public void uncaughtException(Thread t, Throwable e)
    {
      translate(e);
      if(e.getCause()!=null)
      {
        translate(e.getCause());
      }
      e.printStackTrace();
    }
    public void translate(Throwable e)
    {
      java.util.List<StackTraceElement> result = new java.util.ArrayList<StackTraceElement>();
      StackTraceElement[] elements = e.getStackTrace();
      try
      {
        for(StackTraceElement element:elements)
        {
          String className = element.getClassName();
          String methodName = element.getMethodName();
          boolean methodFound = false;
          int index = className.lastIndexOf('.')+1;
          try {
            java.lang.reflect.Method query = this.getClass().getMethod(className.substring(index)+"_"+methodName,new Class[]{});
            UmpleSourceData sourceInformation = (UmpleSourceData)query.invoke(this,new Object[]{});
            for(int i=0;i<sourceInformation.size();++i)
            {
              // To compensate for any offsets caused by injected code we need to loop through the other references to this function
              //  and adjust the start / length of the function.
              int functionStart = sourceInformation.getJavaLine(i) + (("main".equals(methodName))?3:1);
              int functionEnd = functionStart + sourceInformation.getLength(i);
              int afterInjectionLines = 0;
              //  We can leverage the fact that all inject statements are added to the uncaught exception list 
              //   before the functions that they are within
              for (int j = 0; j < i; j++) {
                if (sourceInformation.getJavaLine(j) - 1 >= functionStart &&
                    sourceInformation.getJavaLine(j) - 1 <= functionEnd &&
                    sourceInformation.getJavaLine(j) - 1 <= element.getLineNumber()) {
                    // A before injection, +2 for the comments surrounding the injected code
                    if (sourceInformation.getJavaLine(j) - 1 == functionStart) {
                        functionStart += sourceInformation.getLength(j) + 2;
                        functionEnd += sourceInformation.getLength(j) + 2;
                    } else {
                        // An after injection
                        afterInjectionLines += sourceInformation.getLength(j) + 2;
                        functionEnd += sourceInformation.getLength(j) + 2;
                    }
                }
              }
              int distanceFromStart = element.getLineNumber() - functionStart - afterInjectionLines;
              if(distanceFromStart>=0&&distanceFromStart<=sourceInformation.getLength(i))
              {
                result.add(new StackTraceElement(element.getClassName(),element.getMethodName(),sourceInformation.getFileName(i),sourceInformation.getUmpleLine(i)+distanceFromStart));
                methodFound = true;
                break;
              }
            }
          }
          catch (Exception e2){}
          if(!methodFound)
          {
            result.add(element);
          }
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }
      e.setStackTrace(result.toArray(new StackTraceElement[0]));
    }
  //The following methods Map Java lines back to their original Umple file / line    
    public UmpleSourceData TreasureChest_playerHasKey(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(36).setJavaLines(357).setLengths(1);}
    public UmpleSourceData TreasureChest_unlock(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(60).setJavaLines(85).setLengths(1);}
    public UmpleSourceData TreasureChest_scenarioHeader(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(44).setJavaLines(367).setLengths(3);}
    public UmpleSourceData TreasureChest_forceLock(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(100).setJavaLines(107).setLengths(2);}
    public UmpleSourceData TreasureChest_report(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(40).setJavaLines(362).setLengths(1);}
    public UmpleSourceData TreasureChest_lock(){ return new UmpleSourceData().setFileNames("model.ump","model.ump","model.ump","model.ump").setUmpleLines(70, 70, 90, 90).setJavaLines(129, 132, 142, 145).setLengths(1, 2, 1, 2);}
    public UmpleSourceData TreasureChest_main(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(4).setJavaLines(321).setLengths(30);}
    public UmpleSourceData TreasureChest_timeoutOpenedToClosed(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(85).setJavaLines(188).setLengths(2);}
    public UmpleSourceData TreasureChest_close(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(95).setJavaLines(210).setLengths(1);}
    public UmpleSourceData TreasureChest_getDescription(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(50).setJavaLines(374).setLengths(1);}
    public UmpleSourceData TreasureChest_open(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(75).setJavaLines(167).setLengths(1);}

  }
  public static class UmpleSourceData
  {
    String[] umpleFileNames;
    Integer[] umpleLines;
    Integer[] umpleJavaLines;
    Integer[] umpleLengths;
    
    public UmpleSourceData(){
    }
    public String getFileName(int i){
      return umpleFileNames[i];
    }
    public Integer getUmpleLine(int i){
      return umpleLines[i];
    }
    public Integer getJavaLine(int i){
      return umpleJavaLines[i];
    }
    public Integer getLength(int i){
      return umpleLengths[i];
    }
    public UmpleSourceData setFileNames(String... filenames){
      umpleFileNames = filenames;
      return this;
    }
    public UmpleSourceData setUmpleLines(Integer... umplelines){
      umpleLines = umplelines;
      return this;
    }
    public UmpleSourceData setJavaLines(Integer... javalines){
      umpleJavaLines = javalines;
      return this;
    }
    public UmpleSourceData setLengths(Integer... lengths){
      umpleLengths = lengths;
      return this;
    }
    public int size(){
      return umpleFileNames.length;
    }
  }
}