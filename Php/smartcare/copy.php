<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);
    $id = array();

    $sql = "SELECT * FROM patient WHERE encoder ='$currentUserID' and share_id != '0'";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            array_push($id, $row['share_id']); 
        }

        $count = 0;
        $count1 = 1;
        $size = count($id);
        $size1 = count($id)-1;

        if(count($id) == 1){
            $sql1 = "SELECT * FROM user WHERE user_id ='$id[0]'";
            $result1 = $conn->query($sql1);

                while($row1 = mysqli_fetch_assoc($result1)){
                    echo $row1['staff_name']."`".$row1['user_id']."%";
                }
        }

        else{
            for(;;){
                $size1 = count($id) - 1;

                if($count >= $size1){ //size1 kay ang count abi ga umpisa 0 so need minusan sang isa ang size sang array
                    break;
                }

                else if($id[$count] == $id[$count1]){
                    
                    unset($id[$count1]); 

                    array_splice($id, $size);
                    
                    $size = count($id);

                    if($count1 >= $size){
                        $count++;
                        $count1 = $count + 1;
                    }
                }

                else{
                    $count1++;
                    $size = count($id);

                        if($count1 >= $size){
                            $count++;
                            $count1 = $count + 1;
                        }
                    }
                }

                for($x = 0 ; $x < count($id) ; $x++){
                    $sql1 = "SELECT * FROM user WHERE user_id ='$id[$x]'";
                    $result1 = $conn->query($sql1);

                    while($row1 = mysqli_fetch_assoc($result1)){
                        echo $row1['staff_name']."`".$row1['user_id']."%";
                    }
                }

            }

        

    }

    else{
        echo 'failure';
    }   

}
?>