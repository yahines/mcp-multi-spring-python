from mcp.server.mcpserver import MCPServer

mcp = MCPServer("Python MCP Server")

@mcp.tool()
def get_employee_info(name:str)->dict:
    """
    Get information about given employee name :
    - name
    - salary
    """
    return{
        "employee_name" : name,
        "salary" : 55000,
    }