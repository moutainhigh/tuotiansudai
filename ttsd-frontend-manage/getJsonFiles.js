var fs = require('fs');
var path = require('path');
var basePath = path.join(__dirname, 'resources'),
    outputPath=path.join(basePath, 'develop'); //默认打包路径
//遍历文件夹，获取所有文件夹里面的文件信息
var NODE_ENV=process.env.NODE_ENV;

if(NODE_ENV=='production') {
    outputPath=path.join(basePath, 'prod');
    // 生成生产环境的json文件
}
function getJsonFileList(projectCategory,formatName){
    this.formatName=outputPath+'/'+formatName;
    this.projectCategory=projectCategory;
    this.filesList = [];
    this.jsonFormat={
        "jsFile":{},
        "cssFile":{}
    };
}

getJsonFileList.prototype.mapPath=outputPath+'/assets-resources.json';
//读文件里的内容
getJsonFileList.prototype.readFile = function() {
    var textFile = fs.readFileSync(this.mapPath, 'utf8'); //需要用到同步读取
    textFile=JSON.parse(textFile);
    this.formatHandler(textFile);
}

//写入文件utf-8格式
getJsonFileList.prototype.writeFile = function(data) {
    fs.writeFile(this.formatName,data,'utf-8',function() {
        console.log("文件生成成功");
    });
}

getJsonFileList.prototype.formatHandler = function(textFile) {
    for(var key in textFile){
        var keyObj=key.split('/'),
            keyLen=keyObj.length,
            siteKind=keyObj[1], //站点类型
            outFileName=keyObj[keyLen-1]; //打包后的文件名

        if(siteKind==this.projectCategory || siteKind=='public') {
            var keyNameObj=textFile[key];
            this.jsonFormat['jsFile'][outFileName]=keyNameObj.js;
            this.jsonFormat['cssFile'][outFileName]=keyNameObj.css;
        }
    }
    this.addJqueryPlugin(outputPath+'/public/plugins'); //读取jquery文件
    console.log(this.jsonFormat);
    var strJsonObj=JSON.stringify(this.jsonFormat);
    this.writeFile(strJsonObj);
}

getJsonFileList.prototype.addJqueryPlugin=function(path) {

    var filesList=this.filesList;
    var files = fs.readdirSync(path);//需要用到同步读取
    files.forEach(function(file) {
        var states = fs.statSync(path+'/'+file);
        if(states.isDirectory())
        {
            this.readPluginFloder(path+'/'+file,filesList);
        }
        else {
            var suffix=file.split('.'),
                len=suffix.length;
            if(suffix[len-1]=='js') {
                var keyName=suffix[0];
                this.jsonFormat['jsFile'][keyName]='/public/plugins/'+file;
            }
        }
    }.bind(this))

}

getJsonFileList.prototype.init=function() {
    var that=this;
    //判断打包的时候文件路径是否存在
    fs.exists(this.mapPath, function (exists) {
        if(exists) {
            that.readFile();
        }
    });

}

//ask,web,activity,point,mobile站点打包生成的的json文件名
var getJsonAskList=new getJsonFileList('ask','json-ask.json');
getJsonAskList.init();

var getJsonWebList=new getJsonFileList('web','json-web.json');
getJsonWebList.init();
