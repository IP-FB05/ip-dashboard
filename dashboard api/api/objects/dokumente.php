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
    $query = "SELECT dokumentID, kategorie.name AS 'Kategoriename', dokumente.name, lastChanged, link FROM dokumente JOIN kategorie ON kategorie.kategorieID = dokumente.kategorieID;";
 
    // prepare query statement
    $stmt = $this->conn->prepare($query);
 
    // execute query
    $stmt->execute();
 
    return $stmt;
	}
	// create dokumente
	function create(){
     
        $queryKategorieNameSelect = "SELECT kategorieID FROM kategorie WHERE name LIKE ?";

        $stmt2 = $this->conn->prepare($queryKategorieNameSelect);

        $stmt2->bindParam(1, $this->KategorieName);
 
        // execute query
        $stmt2->execute();

        $num2 = $stmt2->rowCount();
        $katID = 0;

        // check if more than 0 record found
        if($num2>0){
            $row2 = $stmt2->fetch(PDO::FETCH_ASSOC);
            $katID = $row2['kategorieID'];
        }
        else {
            $queryInsert = "INSERT INTO kategorie SET name=:KategorieName";
            $stmt3 = $this->conn->prepare($queryInsert);
            $this->KategorieName=htmlspecialchars(strip_tags($this->KategorieName));
            $stmt3->bindParam(":KategorieName", $this->KategorieName);
            $stmt3->execute();

            $stmt2->execute();
            $row2 = $stmt2->fetch(PDO::FETCH_ASSOC);
            $katID = $row2['kategorieID'];
        }

        echo $katID;
		// query to insert record
		$query = "INSERT INTO dokumente SET kategorieID=:kategorieID, name=:name, lastChanged=CURDATE(), link=:link";
	 
		// prepare query
		$stmt = $this->conn->prepare($query);
	 
		// sanitize
		$this->name=htmlspecialchars(strip_tags($this->name));
		$this->link=htmlspecialchars(strip_tags($this->link));
	 
		// bind values
		$stmt->bindParam(":kategorieID", $katID);
		$stmt->bindParam(":name", $this->name);
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