package com.playdata.moodMusicRecommend.recommend;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;

import org.python.core.PyString;
import org.springframework.stereotype.Service;
import org.python.util.PythonInterpreter;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendService {
    private static PythonInterpreter pinterpreter;
    public Optional<String> recommendMusic(List imageList){
        // Import the mymodule module
//        System.setProperty("python.import.site", "false");
        pinterpreter.exec("import sys");
        pinterpreter.exec("sys.path.append('src/main/')");
        pinterpreter.exec("import pythonAlgo");
//        python.exec("result = pythonAlgo.main.detecting("+imageList.get(0)+","+imageList.get(1)+")");

        pinterpreter = new PythonInterpreter();
//        pinterpreter.exec("import sys");
//        pinterpreter.exec("sys.path.append('../')");
        pinterpreter.execfile("src/main/pythonAlgo/main.py"); // Call the add_squared function in the mymodule module
//        pinterpreter.exec("result = detecting("+imageList.get(0)+","+imageList.get(1)+")"); // Get the result from Pytho
        int result = pinterpreter.get("result", Integer.class);
         System.out.println("Result: " + result);

        PyFunction pyFuntion = (PyFunction) pinterpreter.get("detecting", PyFunction.class);
        PyObject pyobj = pyFuntion.__call__(new PyString((String) imageList.get(0)), new PyString((String) imageList.get(1)));
        System.out.println(pyobj.toString());

        return null;
    }


}
