<?php
$id = 111;
$booltest = is_int($id);
if ($booltest) {
echo 'Invalid ID: "' . $id . '"';
exit();
}

?>