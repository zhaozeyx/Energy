$(function(){
	//折叠菜单
	var Accordion = function(el,multiple){
		this.el = el || {};
		this.multiple = multiple || false;
		var links = this.el.find('.toggle');
		links.on('click',{el:this.el, multiple:this.multiple}, this.dropdown)
	}
	
	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();

		$next.toggle();
		$this.parent().toggleClass('open');

		if (!e.data.multiple) {
			$el.find('.submenu').not($next).hide().parent().removeClass('open');
		};
	}	

	var accordion = new Accordion($('#accordion'), false);
})
