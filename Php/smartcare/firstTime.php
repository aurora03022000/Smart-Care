<?php
if(isset($_POST['admin_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $admin_id = validate($_POST['admin_id']);
    
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM `admin` WHERE admin_id = '$admin_id'";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            if($row['firstTime'] == 1){
                echo 'notFirstTime';
            }

            else if($row['firstTime'] == 0){
                echo 'firstTime';
            }
        } 
     }
 
     else{
         echo 'failure';
     }
}
?>