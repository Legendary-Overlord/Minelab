package gui;

import java.util.ArrayList;
import java.util.Optional;

import exceptions.ExceedMaxStackException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import model.Block;
import model.Inventory;
import model.Stack;

public class MinecraftController {
	
	static boolean ACTIVE_CONSTRUCTION = false;
	static String ACTIVE_QUICKBAR_BLOCK = "";
	static Inventory inv;
	static ArrayList<Stack<Block>[]> quickbar;
	void initialize() {
		inv = new Inventory();
		quickbar = new ArrayList<>();
	}
    @FXML
    private GridPane inventoryGrid;
    @FXML
    private GridPane quickGrid;
    @FXML
    private Label statusModeLabel;
    @FXML
    void insertBlock(ActionEvent event) {
    	TextInputDialog blockDialog = new TextInputDialog("");
    	blockDialog.setTitle("Block Type Dialog");
    	blockDialog.setHeaderText("Select one of the following types of blocks: \n dirt, stone, sand.");
    	blockDialog.setContentText("Please type :");
    	TextInputDialog numDiag = new TextInputDialog("1");
    	numDiag.setTitle("Quantity");
    	numDiag.setHeaderText("How many blocks ?");
    	numDiag.setContentText("type a number:");
    	String blockType = "";
    	Optional<String> blockTyp = blockDialog.showAndWait();
    	if(blockTyp.isPresent())
    		blockType=blockTyp.get();
    	int n = 0;
    	Optional<String> q = numDiag.showAndWait();
    	if(q.isPresent())
    		n=Integer.parseInt(q.get());
		try {
			if(n>64)
				throw new ExceedMaxStackException();
			inv.insertBlock(new Block(blockType), n);
		    updateGUIInventory();
		} catch (ExceedMaxStackException e) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Maximum block number per cell exceeded");
				alert.setContentText("You can only add a maximum of 64 blocks each time.");
				alert.showAndWait();
		}
    }
    @FXML
    void groupBlocks(ActionEvent event) {
    	inv.groupBlocks();
    	updateGUIInventory();
    }
    @FXML
    void nextBlockSet(ActionEvent event) {
    	if(ACTIVE_CONSTRUCTION) {
    		quickbar.clear();
    		quickbar = inv.groupQuickAccess();
    		for(Stack<Block>[] a:quickbar) {
    			if (!a[0].peek().getType().equals(ACTIVE_QUICKBAR_BLOCK)) {
    				updateQuickBar(a);
    				break;
    			}
    		}
    	}
    }
 
    @FXML
    void setModeConstruction(ActionEvent event) {
    	ACTIVE_CONSTRUCTION = true;
    	statusModeLabel.setText("Construction Mode: Active");
    }
    @FXML
    void setQuick(ActionEvent event) {
    	ACTIVE_CONSTRUCTION = false;
    	statusModeLabel.setText("Construction Mode: Deactivated");
    }
    @FXML
    void setQuickBar(ActionEvent event) {
    	if(!ACTIVE_CONSTRUCTION) {
    		TextInputDialog numDiag = new TextInputDialog("");
        	numDiag.setTitle("Set Quick Access Bar");
        	numDiag.setHeaderText("Which block: sand, dirt, stone ?");
        	numDiag.setContentText("type block name:");
        	Optional<String> res = numDiag.showAndWait();
        	if(res.isPresent()) {
        		ACTIVE_QUICKBAR_BLOCK=res.get();
        	}
        	quickbar.clear();
        	quickbar = inv.groupQuickAccess();
        	for(Stack<Block>[] s :quickbar) {
        		if(s[0].peek().getType().equals(ACTIVE_QUICKBAR_BLOCK)) {
        			updateQuickBar(s);
        			break;
        		}
        	}
    	}
    }
    private void updateGUIInventory() {
    	inventoryGrid.getChildren().clear();
    	Stack<Block>[] s = inv.getInv();
    	for(int i=0,j=0,x=0;j<3&&x<36;i++,x++) {
    		if(i<8) {
    			Stack<Block> m = s[x];
    			if(m.isEmpty())
    				break;
    			Label l = new Label(m.peek().getType()+":"+m.size());
    			inventoryGrid.add(l, i, j);
    		}else {
    			i=0;
    			j++;
    		}
    	}
    	
    }
    private void updateQuickBar(Stack<Block>[] blockSet) {
    	quickGrid.getChildren().clear();
    	int n = 0;
    	for(Stack<Block> s:blockSet) {
    		if(n<8) {
    			Label l = new Label(ACTIVE_QUICKBAR_BLOCK+":"+s.size());
        		quickGrid.add(l, n, 0);
        		n++;
    		}else
    			break;
    		
    	}
    }
}
