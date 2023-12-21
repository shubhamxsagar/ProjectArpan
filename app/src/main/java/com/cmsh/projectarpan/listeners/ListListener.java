package com.cmsh.projectarpan.listeners;

import com.cmsh.projectarpan.responses.ListResponse;
import com.cmsh.projectarpan.responses.PageResponse;

public interface ListListener {
    void onListClicked(ListResponse listResponse);
}
