List Statement
data-sly-list statement is used to iterate the values like array/list with in htl.
	Usage:
<ul data-sly-list="${ currentPage.listChildren }">
   <li>  ${item.name} </li>
</ul>
	Where item is a default object for list, we can assign customized object also.   In addition to that itemList is also a default object.

In case of default identifier:

<ul data-sly-list="${currentPage.listChildren}">
   <li data-sly-test="${ itemList.even}">${item.title}</li>
</ul>

Below are the default objects used in the data-sly-list

•	index: zero-based counter (0..length-1)
•	count: one-based counter (1..length).
•	first: true for the first element being iterated.
•	middle: true if element being iterated is neither the first nor the last.
•	last: true for the last element being iterated.
•	odd: true if index is odd.
•	even: true if index is even
•	begin: iteration begins at the item located at the specified index; first item of the collection has index 0
•	step: iteration will only process every step items of the collection, starting with the first one
•	end: iteration ends at the item located at the specified index (inclusive)


Repeat Statement
It is same as List, but the html structure also iterated, whereas list acts as for loop and repeat will iterate the complete structure including the conditional statement as well.  All the objects present in list are also supported by repeat.
<ul data-sly-repeat="${ currentPage.listChildren }">
   <li>  ${item.name} </li>	
</ul>
