<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
 
// get database connection
include_once '../config/database.php';
 
// instantiate system object
include_once '../objects/dokumente.php';
 
$database = new Database();
$db = $database->getConnection();
 
$dokumente = new Dokumente($db);
 
// get posted data
$data = json_decode(file_get_contents("php://input"));
 
    // set dokumente property values
	$dokumente->KategorieName = $data->KategorieName;
    $dokumente->name = $data->name;
    $dokumente->link = $data->link;
 
    // create the system
    if($dokumente->create()){
 
        // set response code - 201 created
        http_response_code(201);
 
        // tell the user
        echo json_encode(array("message" => "Dokumente was created."));
    }
 
    // if unable to create the system, tell the user
    else{
 
        // set response code - 503 service unavailable
        http_response_code(503);
 
        // tell the user
        echo json_encode(array("message" => "Unable to create dokumente."));
    }
?>