<?php
if(isset($_POST['room']) ){
    require_once "conn.php";
    require_once "validate.php";
    
    $room = validate($_POST['room']);

    $sql = "SELECT * FROM room WHERE room_number = '$room' and room_status='Available'";
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