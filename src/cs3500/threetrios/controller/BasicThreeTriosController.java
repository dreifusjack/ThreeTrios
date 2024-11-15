package cs3500.threetrios.controller;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.view.AIPlayer;
import cs3500.threetrios.view.HumanPlayer;
import cs3500.threetrios.view.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

/**
 * Implements the behaviors of ThreeTriosController interface.
 */
public class BasicThreeTriosController implements ThreeTriosController {
  private final GridReader gridConfig;
  private final CardReader cardConfig;
  private TTGUIView view;

  /**
   * Constructs a controller in terms of the given configuration file names.
   *
   * @param gridConfigFileName name of the file with grid configuration
   * @param cardConfigFileName name of the file with card configuration
   * @throws IllegalArgumentException given string files are not found or null
   */
  public BasicThreeTriosController(String gridConfigFileName, String cardConfigFileName) {
    gridConfig = new GridFileReader(gridConfigFileName);
    cardConfig = new CardFileReader(cardConfigFileName);
    // assuming both file readers ensure given names are not null, and are found in their
    // corresponding config file locations. Corresponding classes throw exceptions otherwise.
    view = null;
  }

  @Override
  public void playGame(ThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException();
    }
    // readers gather data from files
    gridConfig.readFile();
    cardConfig.readFile();
    // assuming readers throw exceptions for all invalid file cases
    // (refer to readers interfaces for all cases)

    model.startGame(gridConfig.getGrid(), cardConfig.getCards(), gridConfig.getNumberOfCardCells());
    view = new TTGUIView(model);

//    Player redPlayer = model.getRedPlayer();
//    Player bluePlayer = model.getBluePlayer();
//
//    PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);
//    PlayerActions bluePlayerActions = new AIPlayer(TeamColor.BLUE, new CornerStrategy());
//
//    cs3500.threetrios.view.ThreeTriosController redController = new cs3500.threetrios.view.ThreeTriosController(model, view, redPlayerActions);
//    cs3500.threetrios.view.ThreeTriosController blueController = new cs3500.threetrios.view.ThreeTriosController(model, view, bluePlayerActions);
//
//    model.addModelStatusListener(redController);
//    model.addModelStatusListener(blueController);
//
//    redController.startGame();
  }
}
