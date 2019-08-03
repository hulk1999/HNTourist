<div id="editor-container">
	<div id="editor"></div>
</div>

<script src="<%= request.getContextPath() %>/quill/quill.js"></script>
<script>

	var toolbarOptions = [
	  ['bold', 'italic', 'underline'],
	  [{ 'header': [1, 2, 3, 4, 5, 6, false] }, { 'size' : [] }],
	  [{ 'color':  [] }, { 'background': [] }],
	  [{ 'align': [] }],
	  ['image']
	];

	var editor = new Quill('#editor', {
	modules: {
	    toolbar: toolbarOptions
	  },
	  theme: 'snow'
	});

</script>