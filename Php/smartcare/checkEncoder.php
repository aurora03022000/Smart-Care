<?php
if(isset($_POST['encoder']) ){
    require_once "conn.php";
    require_once "validate.php";
    
    $encoder = validate($_POST['encoder']);

    $sql = "SELECT * FROM user WHERE isExist=1 and staff_name = '$encoder'";
    $result = mysqli_query($conn, $sql);
    $countRow = mysqli_num_rows($result);

    if($result){
        if($countRow > 0){
            echo 'success';
        }

        else{
            echo 'none';
        }
     }
 
     else{
         echo 'failure';
     }

}
?>