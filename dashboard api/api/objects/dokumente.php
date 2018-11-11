<?php
class Dokumente{
 
    // database connection and table name
    private $conn;
    private $table_name = "dokumente";
 
    // object properties
    public $dokumentID;
	public $kategorieID;
    public $name;
    public $lastChanged;
    public $link;
 
    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }
	// read dokumente
	function read(){
 
    // select all query
    $query = "SELECT * FROM dokumente";
 
    // prepare query statement
    $stmt = $this->conn->prepare($query);
 
    // execute query
    $stmt->execute();
 
    return $stmt;
	}
	// create dokumente
	function create(){
	 
		// query to insert record
		$query = "INSERT INTO dokumente SET kategorieID=:kategorieID, name=:name, lastChanged=:lastChanged, link=:link";
	 
		// prepare query
		$stmt = $this->conn->prepare($query);
	 
		// sanitize
		$this->kategorieID=htmlspecialchars(strip_tags($this->kategorieID));
		$this->name=htmlspecialchars(strip_tags($this->name));
		$this->lastChanged=htmlspecialchars(strip_tags($this->lastChanged));
		$this->link=htmlspecialchars(strip_tags($this->link));
	 
		// bind values
		$stmt->bindParam(":kategorieID", $this->kategorieID);
		$stmt->bindParam(":name", $this->name);
		$stmt->bindParam(":lastChanged", $this->lastChanged);
		$stmt->bindParam(":link", $this->link);
	 
		// execute query
		if($stmt->execute()){
			return true;
		}
	 
		return false;
	}
	
	// delete the dokumente
	function delete(){
 
    // delete query
    $query = "DELETE FROM " . $this->table_name . " WHERE dokumentID = ?";
 
    // prepare query
    $stmt = $this->conn->prepare($query);
 
    // sanitize
    $this->dokumentID=htmlspecialchars(strip_tags($this->dokumentID));
 
    // bind id of record to delete
    $stmt->bindParam(1, $this->dokumentID);
 
    // execute query
    if($stmt->execute()){
        return true;
    }
 
    return false;
     
	}
}
?>