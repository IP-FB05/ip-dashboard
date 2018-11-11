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
}
?>