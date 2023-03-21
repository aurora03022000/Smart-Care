<?php
if(isset($_POST['admin_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $admin_id = validate($_POST['admin_id']);
    
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM hospital_information WHERE admin_id = '$admin_id '";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['hospital_name'];
        } 
     }
 
     else{
         echo 'failure';
     }
}
?>