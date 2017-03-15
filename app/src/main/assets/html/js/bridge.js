function connectWebViewJavascriptBridge(callback) {
                    if (window.WebViewJavascriptBridge) {
                        console.log('has connect');
                        callback(WebViewJavascriptBridge)
                    } else {
                        console.log('has not connect');
                        document.addEventListener(
                            'WebViewJavascriptBridgeReady'
                            , function() {
                                callback(WebViewJavascriptBridge)
                            },
                            false
                        );
                    }
                }
