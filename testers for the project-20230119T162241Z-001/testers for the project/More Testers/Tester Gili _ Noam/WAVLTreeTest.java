import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class WAVLTreeTest {
	private WAVLTree insertCaseOne = new WAVLTree();
	private WAVLTree insertCaseDoubleRotation = new WAVLTree();
	private WAVLTree insertCaseSingleRotation = new WAVLTree();
	private WAVLTree oneToElevenTree = new WAVLTree();
	private WAVLTree sixToOneTree = new WAVLTree();
	private WAVLTree deletePredecessorTree = new WAVLTree();
	private WAVLTree deletePredecessorWithUnaryNode = new WAVLTree();
	private WAVLTree deleteSuccessorLeafTree = new WAVLTree();
	private WAVLTree deleteSuccessorWithUnaryNodeTree = new WAVLTree();
	private WAVLTree testerFailureTree = new WAVLTree();
	private WAVLTree deleteRootTree = new WAVLTree();
	// TODO test deleteLeaf, testRotations
	private Random RANDOM = new Random();

	private void buildDeleteRootTree() {
		deleteRootTree.insert(1, "");
	}
	
	private void buildInsertCaseOneTree() {
		insertCaseOne.insert(0, "0");
	}
	
	private void buildTesterFailureTree () {
		testerFailureTree.insert(8815, "");
		testerFailureTree.insert(8994, "");
		testerFailureTree.insert(384, "");
		testerFailureTree.insert(7581, "");
	}
	
	private void buildDeletePredecessorTree() {
		deletePredecessorTree.insert(10, "0");
		deletePredecessorTree.insert(5, "0");
		deletePredecessorTree.insert(15, "0");
		deletePredecessorTree.insert(2, "0");
		deletePredecessorTree.insert(8, "0");
		deletePredecessorTree.insert(11, "0");
		deletePredecessorTree.insert(16, "0");
		deletePredecessorTree.insert(1, "0");
		deletePredecessorTree.insert(9, "0");
	}
	
	private void buildDeletePredecessorWithUnaryNode() {
		deletePredecessorWithUnaryNode.insert(10, "0");
		deletePredecessorWithUnaryNode.insert(5, "0");
		deletePredecessorWithUnaryNode.insert(15, "0");
		deletePredecessorWithUnaryNode.insert(2, "0");
		deletePredecessorWithUnaryNode.insert(8, "0");
		deletePredecessorWithUnaryNode.insert(11, "0");
		deletePredecessorWithUnaryNode.insert(16, "0");
		deletePredecessorWithUnaryNode.insert(1, "0");
		deletePredecessorWithUnaryNode.insert(7, "0");
		deletePredecessorWithUnaryNode.insert(12, "0");
	}
	
	
	private void buildInsertCaseDoubleRotation() {
		insertCaseDoubleRotation.insert(0, "0");
		insertCaseDoubleRotation.insert(1, "0");
		insertCaseDoubleRotation.insert(-5, "0");
		insertCaseDoubleRotation.insert(-3, "0");
		insertCaseDoubleRotation.insert(-7, "0");
	}
	
	private void buildInsertCaseSingleRotation() {
		insertCaseSingleRotation.insert(0, "0");
		insertCaseSingleRotation.insert(1, "0");
		insertCaseSingleRotation.insert(-5, "0");
		insertCaseSingleRotation.insert(-2, "0");
		insertCaseSingleRotation.insert(-6, "0");
	}
	
	private void buildOneToSixTree() {		
		for (int i=0; i < 12; i++) {
			oneToElevenTree.insert(i, ""+i);
		}
	}
	
	private void buildSixToOneTree() {		
		sixToOneTree = new WAVLTree();
		for (int i=5; i >= 0; i--) {
			sixToOneTree.insert(i, ""+i);
		}
	}
	
	private void buildDeleteSuccessorLeafTree() {
		deleteSuccessorLeafTree.insert(10, "0");
		deleteSuccessorLeafTree.insert(5, "0");
		deleteSuccessorLeafTree.insert(15, "0");
		deleteSuccessorLeafTree.insert(2, "0");
		deleteSuccessorLeafTree.insert(8, "0");
		deleteSuccessorLeafTree.insert(13, "0");
		deleteSuccessorLeafTree.insert(16, "0");
		
	}
	
	private void buildDeleteSuccessorWithUnaryNodeTree() {//TODO is there any case like that?
		deleteSuccessorWithUnaryNodeTree.insert(10, "0");
		deleteSuccessorWithUnaryNodeTree.insert(5, "0");
		deleteSuccessorWithUnaryNodeTree.insert(15, "0");
		deleteSuccessorWithUnaryNodeTree.insert(2, "0");
		deleteSuccessorWithUnaryNodeTree.insert(8, "0");
		deleteSuccessorWithUnaryNodeTree.insert(13, "0");
		deleteSuccessorWithUnaryNodeTree.insert(16, "0");
		deleteSuccessorWithUnaryNodeTree.insert(1, "0");
		deleteSuccessorWithUnaryNodeTree.insert(7, "0");
		deleteSuccessorWithUnaryNodeTree.insert(9, "0");
		deleteSuccessorWithUnaryNodeTree.insert(11, "0");
		deleteSuccessorWithUnaryNodeTree.insert(20, "0");
	}
	
	
	
	@Before 
	public void setUp() {
		buildDeleteRootTree();
		buildTesterFailureTree();
		buildInsertCaseOneTree();
		buildInsertCaseSingleRotation();
		buildInsertCaseDoubleRotation();
		buildOneToSixTree();
		buildSixToOneTree();
		buildDeletePredecessorTree();
		buildDeletePredecessorWithUnaryNode();
		buildDeleteSuccessorLeafTree();
		buildDeleteSuccessorWithUnaryNodeTree();
	}
	
	@Test 
	public void testDeleteRoot() {
		deleteRootTree.delete(1);
		Assert.assertEquals(deleteRootTree.keysToArray().length, 0);
		Assert.assertEquals(deleteRootTree.getPreOrderString(), "");
	}
	
	@Test
	public void testTesterFailureTree() {
		testerFailureTree.insert(780, "");
		Assert.assertTrue(testerFailureTree.isLeagle());
		Assert.assertEquals("881578038475818994", testerFailureTree.getPreOrderString());
	}
	
	@Test
	public void testDeleteSuccessorLeaf() {
		int origSize = deleteSuccessorLeafTree.size();
		deleteSuccessorLeafTree.delete(15);
		Assert.assertEquals(origSize-1, deleteSuccessorLeafTree.size());
		Assert.assertEquals("105281613", deleteSuccessorLeafTree.getPreOrderString());
	}
	
	@Test
	public void testDeleteSuccessorWithUnaryNode() {
		//TODO this takes the predecessor.
		int origSize = deleteSuccessorWithUnaryNodeTree.size();
		deleteSuccessorWithUnaryNodeTree.delete(15);
		Assert.assertEquals(origSize-1, deleteSuccessorWithUnaryNodeTree.size());
	}
	
	@Test
	public void testDeletePredecessorWithUnaryNode(){
		deletePredecessorWithUnaryNode.delete(10);
		Assert.assertEquals("8521715111216", deletePredecessorWithUnaryNode.getPreOrderString());
		Assert.assertTrue(deletePredecessorWithUnaryNode.isLeagle());
	}
	
	@Test
	public void testDeletePredecessor() {
		int origSize = deletePredecessorTree.size();
		deletePredecessorTree.delete(5);
		Assert.assertEquals("102189151116", deletePredecessorTree.getPreOrderString());
		Assert.assertEquals(origSize-1, deletePredecessorTree.size());
		Assert.assertTrue(deletePredecessorTree.isLeagle());
	}
	
	@Test
	public void testDeleteRootPredecessor() {
		int origSize = deletePredecessorTree.size();
		deletePredecessorTree.delete(10);
		Assert.assertEquals(origSize-1, deletePredecessorTree.size());
		Assert.assertEquals("11521891516", deletePredecessorTree.getPreOrderString());
		Assert.assertTrue(deletePredecessorTree.isLeagle());
	}

	@Test
	public void testCaseOne() {
		insertCaseOne.insert(1, "1");
		Assert.assertTrue(insertCaseOne.isLeagle());
		int[] expKeyArray = {0, 1};
		Assert.assertArrayEquals(expKeyArray, insertCaseOne.keysToArray());
		Assert.assertEquals("01", insertCaseOne.getPreOrderString());
	}
	
	@Test
	public void testCaseSingleRotation() { //TODO make sure this is the case
		insertCaseDoubleRotation.insert(-4, "-1");
		Assert.assertTrue(insertCaseDoubleRotation.isLeagle());
		int[] expKeyArray = {-7,-5,-4,-3,0,1};
		Assert.assertArrayEquals(expKeyArray, insertCaseDoubleRotation.keysToArray());
		Assert.assertEquals("-3-5-7-401", insertCaseDoubleRotation.getPreOrderString());
		
	}
	
	@Test
	public void testCaseDoubleRotation() {
		insertCaseSingleRotation.insert(-7, "0");
		Assert.assertTrue(insertCaseSingleRotation.isLeagle());
		int[] expKeyArray = {-7,-6,-5,-2,0,1};
		Assert.assertArrayEquals(expKeyArray, insertCaseSingleRotation.keysToArray());
		Assert.assertEquals("-5-6-70-21", insertCaseSingleRotation.getPreOrderString());
	}
	
	@Test
	public void testOneToEleven() {
		Assert.assertTrue(oneToElevenTree.isLeagle());
		Assert.assertEquals(12, oneToElevenTree.size());
	}
	
	@Test
	public void testInsertSixToOne() {
		Assert.assertTrue(sixToOneTree.isLeagle());
	}
	
	@Test
	public void testDeleteRandomOneToSix() {
		int numToDelete = RANDOM.nextInt(9) + 2;
		oneToElevenTree.delete(numToDelete);
		Assert.assertTrue(oneToElevenTree.isLeagle());
		Assert.assertEquals(-1, Arrays.toString(oneToElevenTree.keysToArray()).indexOf(""+numToDelete));
	}
	
	@Test
	public void testDeleteRandomSixToOne() {
		int numToDelete = RANDOM.nextInt(5);
		sixToOneTree.delete(numToDelete);
		Assert.assertTrue(sixToOneTree.isLeagle());
		Assert.assertEquals(-1, Arrays.toString(sixToOneTree.keysToArray()).indexOf(""+numToDelete));
	}
	
	@Test
	public void testFindSuccessor() {
		int num = RANDOM.nextInt(4);
		Assert.assertEquals(num+1, oneToElevenTree.findSuccessor(oneToElevenTree.search(num, oneToElevenTree.getRoot())).getKey());
		Assert.assertEquals(num+1, oneToElevenTree.findSuccessor(sixToOneTree.search(num, sixToOneTree.getRoot())).getKey());
	}
	
	@Test
	public void testFindPredecessor() {
		int num = RANDOM.nextInt(3)+1;
		Assert.assertEquals(num-1, oneToElevenTree.findPredecessor(oneToElevenTree.search(num, oneToElevenTree.getRoot())).getKey());
		Assert.assertEquals(num-1, sixToOneTree.findPredecessor(sixToOneTree.search(num, sixToOneTree.getRoot())).getKey());
	}
	
	@Test
	public void deleteThenInsert() {
		int numToDelete = RANDOM.nextInt(11);
		oneToElevenTree.delete(numToDelete);
		oneToElevenTree.insert(numToDelete, "");
		Assert.assertTrue(oneToElevenTree.isLeagle());
	}
}
