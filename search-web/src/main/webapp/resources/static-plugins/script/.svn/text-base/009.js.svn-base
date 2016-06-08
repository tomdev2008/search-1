/**
 * ebay品牌故事
 */
$(document).ready(
				function() {
					var $marginBox = $('#J_brandBox');
					var $txtBox = $('#J_brandInfoText'), txtHeight = $txtBox.height(), txtHtml = $txtBox.html(), $txtBtnBox = $('#showMoreTxt');
					var ie6 = $.browser.msie && $.browser.version=='6.0';
					if (txtHeight > 110) {
						$txtBox.css({
							height : '110px',
							overflow : 'hidden'
						}).data('nowHeight', 110);
						if(ie6)
							$marginBox.css({height : '110px'});
						$('<a href="#" class="down" ></a>').click(
								function() {
									if ($txtBox.data('nowHeight') != 110) {
										if ($txtBox.data('jscroll'))
											$txtBox.removeData('jscroll').html(
													txtHtml);
										$txtBox.css({
											height : '110px',
											overflow : 'hidden'
										}).data('nowHeight', 110);
										if(ie6)
											$marginBox.css({height : '110px'});
									} else {
										if(txtHeight > 176){
											$txtBox.css({
												height : '176px'
											}).data('nowHeight', 176).jscroll({
												W : "7px",
												Bg : "right 0 repeat-y",
												Btn : {
													btn : false
												},
												Fn : function() {
												}
											})
											if(ie6)
												$marginBox.css({height : '176px'});
										}else{
											$txtBox.css({
												height : txtHeight + 'px',
												overflow : 'hidden'
											}).data('nowHeight', txtHeight);
											if(ie6)
												$marginBox.css({height : txtHeight + 'px'});
										}
									}
									$(this).toggleClass('up');
									return false;
								}).appendTo($txtBtnBox);
					} else {
						$txtBox.css({
							height : '110px',
							overflow : 'hidden'
						}).data('nowHeight', 110);
						if(ie6)
							$marginBox.css({height : '110px'});
					}
				});
