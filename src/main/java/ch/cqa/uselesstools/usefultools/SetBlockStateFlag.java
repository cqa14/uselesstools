package ch.cqa.uselesstools.usefultools;

public enum SetBlockStateFlag {

  BLOCK_UPDATE(1),
  SEND_TO_CLIENTS(2),
  DO_NOT_RENDER(4),
  RUN_RENDER_ON_MAIN_THREAD(8),
  PREVENT_NEIGHBOUR_REACTIONS(16),
  NEIGHBOUR_REACTIONS_DONT_SPAWN_DROPS(32),
  BLOCK_IS_BEING_MOVED(64);

  public static int get(SetBlockStateFlag... flags) {
    int result = 0;
    for (SetBlockStateFlag flag : flags) {
      result |= flag.flagValue;
    }
    return result;
  }

  SetBlockStateFlag(int flagValue) {this.flagValue = flagValue;}
  private int flagValue;
}
