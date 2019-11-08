package com.sky.test.cli;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

import lombok.Data;

@Data
public class JCommanderExample {
  @Parameter(names="-p",required=true,description="jc params")
  private List<String> parameters = new ArrayList<String>();
 
  @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
  private Integer verbose = 1;
 
  @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
  private String groups;
 
  @Parameter(names = "-debug", description = "Debug mode")
  private boolean debug = false;
}
