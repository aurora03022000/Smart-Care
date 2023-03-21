<?php
if(isset($_POST['room_number']) ){
    require_once "conn.php";
    require_once "validate.php";
    
    $room_number = validate($_POST['room_number']);

    $sql = "SELECT * FROM room WHERE room_number='$room_number'";
    $result = mysqli_query($conn, $sql);
    $countRow = mysqli_num_rows($result);

    if($countRow > 0){
        echo 'taken';
    }

    else if($countRow == 0){
       $insertRoom = "INSERT INTO room(`room_number`, `room_status`) VALUES('$room_number', 'Available')";
       $insertRoomResult = mysqli_query($conn, $insertRoom);

       if($insertRoomResult){
        echo 'success';
       }

       else{
        echo 'failure';
       }
    }
 
     else{
         echo 'failure';
     }

}
?>