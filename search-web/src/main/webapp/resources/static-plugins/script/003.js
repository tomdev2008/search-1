//GA插码
var _gaq = _gaq || [];
_gaq.push([ '_setAccount', 'UA-9540643-5' ]);
_gaq.push([ '_addOrganic', 'soso', 'w' ]);
_gaq.push([ '_addOrganic', 'youdao', 'q' ]);
_gaq.push([ '_addOrganic', 'baidu', 'word' ]);
_gaq.push([ '_addOrganic', 'sogou', 'query' ]);
_gaq.push([ '_setDomainName', '.xiu.com' ]);
_gaq.push([ '_setAllowLinker', true ]);
_gaq.push([ '_setAllowHash', false ]);
_gaq.push([ '_trackPageview' ]);
_gaq.push([ '_trackPageLoadTime' ]);

(function() {
	var ga = document.createElement('script');
	ga.type = 'text/javascript';
	ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl'
			: 'http://www')
			+ '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(ga, s);
})();