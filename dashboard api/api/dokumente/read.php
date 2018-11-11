<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
include_once '../config/database.php';
include_once '../objects/dokumente.php';
 
// instantiate database and product object
$database = new Database();
$db = $database->getConnection();
 
// initialize object
$dokumente = new Dokumente($db);
// query products
$stmt = $dokumente->read();
$num = $stmt->rowCount();
 
// check if more than 0 record found
if($num>0){
 
    // products array
    $dokument_arr=array();
    $dokumente_arr["dokumente"]=array();
 
    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);
 
        $dokument_item=array(
            "dokumentID" => $dokumentID,
			"kategorieID" => $kategorieID,
			"name" => $name,
			"lastChanged" => $lastChanged,
			"link" => $link,
        );
 
        array_push($dokumente_arr["dokumente"], $dokument_item);
    }
 
    // set response code - 200 OK
    http_response_code(200);
 
    // show products data in json format
    echo json_encode($dokumente_arr);
}
else{
 
    // set response code - 404 Not found
    http_response_code(404);
 
    // tell the user no dokumente found
    echo json_encode(
        array("message" => "No dokumente found.")
    );
}
?>