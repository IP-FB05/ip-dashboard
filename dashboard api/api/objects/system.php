<?php
class System{
 
    // database connection and table name
    private $conn;
    private $table_name = "systeme";
 
    // object properties
    public $systemID;
    public $beschreibung;
    public $link;
    public $name;
 
    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }
	// read systems
	function read(){
 
    // select all query
    $query = "SELECT * FROM systeme";
 
    // prepare query statement
    $stmt = $this->conn->prepare($query);
 
    // execute query
    $stmt->execute();
 
    return $stmt;
	}
	// create system
	function create(){
	 
		// query to insert record
		$query = "INSERT INTO systeme SET name=:name, beschreibung=:beschreibung, link=:link";
	 
		// prepare query
		$stmt = $this->conn->prepare($query);
	 
		// sanitize
		$this->name=htmlspecialchars(strip_tags($this->name));
		$this->beschreibung=htmlspecialchars(strip_tags($this->beschreibung));
		$this->link=htmlspecialchars(strip_tags($this->link));
	 
		// bind values
		$stmt->bindParam(":name", $this->name);
		$stmt->bindParam(":beschreibung", $this->beschreibung);
		$stmt->bindParam(":link", $this->link);
	 
		// execute query
		if($stmt->execute()){
			return true;
		}
	 
		return false;
	}
	
	// delete the system
	function delete(){
 
    // delete query
    $query = "DELETE FROM " . $this->table_name . " WHERE systemID = ?";
 
    // prepare query
    $stmt = $this->conn->prepare($query);
 
    // sanitize
    $this->systemID=htmlspecialchars(strip_tags($this->systemID));
 
    // bind id of record to delete
    $stmt->bindParam(1, $this->systemID);
 
    // execute query
    if($stmt->execute()){
        return true;
    }
 
    return false;
     
	}
}
?>