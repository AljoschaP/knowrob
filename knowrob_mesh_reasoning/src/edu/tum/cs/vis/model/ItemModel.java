package edu.tum.cs.vis.model;

import edu.tum.cs.vis.model.parser.ModelParser;

/**
 * Holds information about the 3D Model of an ItemBase object.
 * 
 * @author Stefan Profanter
 *
 */
public class ItemModel {

	/**
	 * Physical file path to the model
	 */
	private String path;
	
	/**
	 * The parser for the model.
	 */
	private ModelParser parser;
		
	/**
	 * Constructor.
	 * The model will immediately be parsed and hold in RAM.
	 * @param path Path to the model
	 */
	public ItemModel(String path) {
		this.path = path;
		if (!parseModel())
			parser = null;
	}

	/**
	 * Get physical file path of model
	 * @return physical file path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set physical file path of model
	 * @param path physical file path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Get associated model parser which contains the model for this item 
	 * @return the model parser
	 */
	public ModelParser getParser() {
		return parser;
	}

	/**
	 * Set associated model parser which contains the model for this item 
	 * @param parser the model parser
	 */
	public void setParser(ModelParser parser) {
		this.parser = parser;
	}
	
	/**
	 * Selects automagically the correct parser for the file (by file extension) and parses
	 * the model with the selected parser.
	 * @return true if successfully parsed, false otherwise
	 */
	public boolean parseModel() {
		if (path == null)
			return false;
		Class<? extends ModelParser> cl = ModelParser.findParser(path);
		if (cl == null)
		{
			System.out.println("No parser found for: " + path);
			return false;
		}
		try {
			parser = (ModelParser) cl.newInstance();
		} catch (InstantiationException e) {
			System.out.println("Couldn't instantiate parser for: " + path);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Couldn't instantiate parser for: " + path);
			e.printStackTrace();
		}
		return parser.parseModel(path);
	}

}