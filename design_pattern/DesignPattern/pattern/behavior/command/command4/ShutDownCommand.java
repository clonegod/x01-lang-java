package command4;
/* The Command for shutting down the computer*/
 class ShutDownCommand implements Command {
   private Computer computer;
 
   public ShutDownCommand(Computer computer) {
      this.computer = computer;
   }
 
   public void execute(){
      computer.shutDown(); // ShutDownCommand命令处理器调用computer的shutDown()
   }
}