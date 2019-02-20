package server;

import api.HTTPMethods;

public interface HTTPModuleFactory {

    HTTPMethods create();
}
