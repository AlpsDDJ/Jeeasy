package org.jeeasy.generate.generator.directive;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class WebFiled extends Directive {

    @Override
    public String getName() {
        return "wf";
    }

    @Override
    public int getType() {
        return Directive.LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        SimpleNode sn_region = (SimpleNode) node.jjtGetChild(0);
        String region = (String) sn_region.value(context);
        SimpleNode sn_key = (SimpleNode) node.jjtGetChild(1);
        //Serializable key = (Serializable) sn_key.value(context);

        SimpleNode sn_data = (SimpleNode) node.jjtGetChild(2);
        //Object data = sn_data.value(context);
        //Map map = new HashMap();
        //map.put("data", data);
//		String vel = HostUtil.getResponseText("http://127.0.0.1/index.html");
//        String vel = "#foreach($member in $data.entrySet())<li>$member.key - $member.value</li>#end ";
        writer.write("renderTemplate(map)");
        return true;
    }

    public static String renderTemplate(Map params) {
        VelocityContext context = new VelocityContext(params);
        StringWriter writer = new StringWriter();
        writer.write(params.toString());
        return writer.toString();
    }
}
